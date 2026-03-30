package com.example.sms.services;

import com.example.sms.dao.CourseDao;
import com.example.sms.dao.CourseEnrollmentDao;
import com.example.sms.dao.StudentDao;
import com.example.sms.dto.EnrollmentDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Course;
import com.example.sms.entity.Enrollment;
import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Status;
import com.example.sms.exception.DuplicateExceptionResource;
import com.example.sms.exception.MaxLimitExceptionResource;
import com.example.sms.exception.NotFoundExceptionResource;
import com.example.sms.util.APIMessage;
import com.example.sms.util.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CourseEnrollmentService {


    @Autowired
    private CourseEnrollmentDao courseEnrollmentDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Transactional(rollbackOn = Exception.class)
    public ResponseModel insertEnrollment(EnrollmentDTO dto) {

        Course course = courseDao.findById(dto.getCourseId());
        if (course == null) {
            throw new NotFoundExceptionResource(APIMessage.COURSE_NOT_FOUND);
        } else if (course.getCurrentEnrollment() == course.getMaxEnrollment()) {
            throw new MaxLimitExceptionResource(APIMessage.ENROLLMENT_MAX_LIMIT_REACHED);
        }

        Student student = studentDao.findById(dto.getStudentId());
        if (student == null) {
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
        }
        boolean studentAlreadyEnrolled = courseEnrollmentDao.existsByStudent_StudentIdAndCourse_CourseId(dto.getStudentId(), dto.getCourseId());
        if (studentAlreadyEnrolled) {
            throw new DuplicateExceptionResource(APIMessage.STUDENT_ALREADY_ENROLLED);
        }
        course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
        courseDao.save(course);
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        courseEnrollmentDao.save(enrollment);
        return ResponseModel.created(
                APIMessage.ENROLLMENT_SUCCESS,
                EnrollmentDTO.toDto(enrollment)
        );
    }

    public ResponseModel getAll(int pageSize, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Enrollment> enrollments = courseEnrollmentDao.findAll(pageable);

        List<EnrollmentDTO> enrollmentDTOS = enrollments.getContent()
                .stream()
                .map(EnrollmentDTO::toDto)
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("data", enrollmentDTOS);
        result.put("pageResult", Utils.preparePageResult(enrollments));

        return ResponseModel.success(
                APIMessage.ENROLLMENT_FETCH_SUCCESS,
                result
        );
    }

    public ResponseModel getEnrollmentById(String enrollmentId) {
        Enrollment enrollment = courseEnrollmentDao.findById(enrollmentId);
        if (enrollment == null) {
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }

        return ResponseModel.success(
                APIMessage.ENROLLMENT_FETCH_SUCCESS,
                EnrollmentDTO.toDto(enrollment)
        );
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseModel updateEnrollmentById(String enrollmentId, EnrollmentDTO dto) {

        Enrollment enrollment = courseEnrollmentDao.findById(enrollmentId);
        if (enrollment == null) {
            return ResponseModel.not_found(APIMessage.ENROLLMENT_NOT_FOUND, null);
        }

        // Declared outside — same pattern as DepartmentService hod/instructors
        Student student = null;
        if (dto.getStudentId() != null && !dto.getStudentId().equals(enrollment.getStudent().getStudentId())) {
            student = studentDao.findById(dto.getStudentId());
            if (student == null) {
                return ResponseModel.not_found(APIMessage.STUDENT_NOT_FOUND, null);
            }

            String courseId = enrollment.getCourse().getCourseId();
            boolean studentAlreadyEnrolled = courseEnrollmentDao
                    .existsByStudent_StudentIdAndCourse_CourseId(dto.getStudentId(), courseId);
            if (studentAlreadyEnrolled) {
                return ResponseModel.conflict(APIMessage.STUDENT_ALREADY_ENROLLED, null);
            }
        }

        Course course = null;
        if (dto.getCourseId() != null && !dto.getCourseId().equals(enrollment.getCourse().getCourseId())) {
            course = courseDao.findById(dto.getCourseId());
            if (course == null) {
                return ResponseModel.not_found(APIMessage.COURSE_NOT_FOUND, null);
            }

            String studentId = enrollment.getStudent().getStudentId();
            boolean studentAlreadyEnrolled = courseEnrollmentDao.existsByStudent_StudentIdAndCourse_CourseId(studentId, dto.getCourseId());
            if (studentAlreadyEnrolled) {
                return ResponseModel.conflict(APIMessage.STUDENT_ALREADY_ENROLLED, null);
            }
        }

        enrollment = dto.toUpdateEntity(enrollment, student, course);
        courseEnrollmentDao.save(enrollment);

        return ResponseModel.success(APIMessage.ENROLLMENT_UPDATE_SUCCESS, EnrollmentDTO.toDto(enrollment));
    }

    public ResponseModel deleteEnrollmentById(String enrollmentId) {
        Enrollment enrollment = courseEnrollmentDao.findById(enrollmentId);
        if (enrollment == null) {
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }

        Course course = enrollment.getCourse();
        course.setCurrentEnrollment(course.getCurrentEnrollment() - 1);
        courseDao.save(course);

        courseEnrollmentDao.deleteById(enrollmentId);

        return ResponseModel.success(
                APIMessage.ENROLLMENT_DELETE_SUCCESS,
                null
        );

    }

}


