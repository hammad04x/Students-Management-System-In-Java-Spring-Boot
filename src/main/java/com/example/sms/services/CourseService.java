package com.example.sms.services;

import com.example.sms.dao.CourseDao;
import com.example.sms.dao.InstructorDao;
import com.example.sms.dto.CourseDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Course;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Category;
import com.example.sms.entity.enums.Status;
import com.example.sms.exception.NotFoundExceptionResource;
import com.example.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private InstructorDao instructorDao;

    public ResponseModel insertCourse(CourseDTO courseDTO) {
        log.info("Insert Courese Method Executed");
        Instructor instructor = instructorDao.findById(courseDTO.getInstructorId());
        if (instructor == null) {
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null
            );
        }

        boolean courseNameExist = courseDao.existByName(courseDTO.getCourseName());
        if (courseNameExist) {
            return ResponseModel.conflict(
                    APIMessage.COURSE_ALREADY_PRESENT,
                    null
            );
        }

        log.debug("All Validation Done Ready To Insert new Course");
        Course course = courseDTO.toEntity(instructor);
        course = courseDao.save(course);
        log.info("Insert Course Method Execution End Preparing Response");
        return ResponseModel.success(
                APIMessage.COURSE_CREATED,
                CourseDTO.toDTO(course)
        );

    }

    public ResponseModel getAllCourse(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Course> courses = courseDao.getAllCourseByStatus(Status.ACTIVE, pageable);

        List<CourseDTO> dtos = courses.getContent()
                .stream()
                .map(CourseDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", courses.getTotalElements());
        pageResult.put("pageCount", courses.getTotalPages());


        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.COURSE_FOUND,
                result
        );

    }

    public ResponseModel getCourseById(String courseId) {

        Course course = courseDao.findById(courseId);

        if (course == null) {
            return ResponseModel.not_found(
                    APIMessage.COURSE_NOT_FOUND,
                    null
            );
        }

        return ResponseModel.success(
                APIMessage.COURSE_FOUND,
                CourseDTO.toDTO(course)
        );
    }

    public ResponseModel updateCourseById(String courseId, CourseDTO dto) {

        Course course = courseDao.findById(courseId);
        if (courseId == null) {
            return ResponseModel.not_found(
                    APIMessage.COURSE_NOT_FOUND,
                    null
            );
        }

        boolean courseExist = courseDao.existByName(dto.getCourseName());
        if (courseExist) {
            return ResponseModel.conflict(
                    APIMessage.COURSE_ALREADY_PRESENT,
                    null
            );
        }

        if (dto.getInstructorId() != null) {
            Instructor instructor = instructorDao.findById(dto.getInstructorId());
            if (instructor == null) {
                return ResponseModel.not_found("Instructor not found", null);
            }
            course.setInstructorId(instructor);
        }

        course = dto.toUpdateEntity(course);
        course = courseDao.save(course);

        return ResponseModel.success(
                APIMessage.COURSE_UPDATED,
                CourseDTO.toDTO(course)
        );

    }

    public ResponseModel deleteCourseById(String string) {
        Course course = courseDao.findById(string);
        if (course == null) {
            return ResponseModel.not_found(
                    APIMessage.COURSE_NOT_FOUND,
                    null
            );
        }
        course.setStatus(Status.DELETED);
        courseDao.save(course);
        return ResponseModel.success(
                APIMessage.COURSE_DELETED,
                CourseDTO.toDTO(course)
        );
    }

    public ResponseModel filterByCategory(String name, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageSize - 1, pageNo);

        try {
            Page<Course> courses = courseDao.filterAllByCategory(Category.valueOf(name.toUpperCase()), pageable);

            List<CourseDTO> dtos = courses.getContent()
                    .stream()
                    .map(CourseDTO::toDTO)
                    .toList();

            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("pageSize", pageSize);
            pageResult.put("pageNo", pageNo);
            pageResult.put("totalRecords", courses.getTotalElements());
            pageResult.put("pageCount", courses.getTotalPages());

            Map<String, Object> result = new HashMap<>();
            result.put("data", dtos);
            result.put("pageResult", pageResult);

            return ResponseModel.success(
                    APIMessage.COURSE_FOUND,
                    result
            );
        } catch (IllegalArgumentException e) {
            throw new NotFoundExceptionResource(APIMessage.CATEGORY_NOT_FOUND);
        } catch (Exception e) {
            throw e;
        }


    }
}