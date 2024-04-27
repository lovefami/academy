package com.group3.finalprojectbe;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import com.group3.finalprojectbe.system.entity.CourseTypeEntity;
import com.group3.finalprojectbe.system.repo.CourseRepository;
import com.group3.finalprojectbe.system.repo.CourseTypeRepository;
import com.group3.finalprojectbe.system.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class InitialDatabase implements ApplicationRunner {
    private final CourseTypeRepository courseTypeRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initialCourseType();
        initCourse();
    }

    private void initialCourseType() {
        long count = courseTypeRepository.count();
        List<CourseTypeEntity> list = new ArrayList<>(3);
        CourseTypeEntity java = CourseTypeEntity.builder().name("JAVA")
                .description("Java is a powerful and versatile programming language, widely used for enterprise-level application development and cross-platform applications. Its strong typing system and extensive library support enable developers to build high-performance and reliable applications.")
                .image(readImage("java")).build();
        CourseTypeEntity c = CourseTypeEntity.builder().name("C#")
                .description("C# is a modern, object-oriented programming language developed by Microsoft. It is widely used for developing Windows applications, games using Unity, and extensive enterprise software. C# simplifies coding with its concise syntax, powerful library, and compatibility with the .NET framework.")
                .image(readImage("c")).build();
        CourseTypeEntity css = CourseTypeEntity.builder().name("CSS")
                .description("CSS is a stylesheet language that defines the presentation of HTML documents. CSS3, its latest iteration, introduces responsive design features like animations, grid layouts, and transitions, enhancing user interfaces and experiences.")
                .image(readImage("css")).build();
        CourseTypeEntity html = CourseTypeEntity.builder().name("HTML")
                .description("HTML is the foundational markup language for creating web pages. It structures web content with elements like headers, paragraphs, and links. HTML5, the latest standard, supports multimedia capabilities and interactive elements.")
                .image(readImage("html")).build();
        CourseTypeEntity javascrip = CourseTypeEntity.builder().name("JavaScript")
                .description("JavaScript is a dynamic scripting language essential for interactive websites. It runs on the client side, allowing for real-time content updates, interactive maps, animated graphics, and complex user interfaces. JavaScript is supported by all modern web browsers.")
                .image(readImage("js")).build();
        list.add(java);
        list.add(c);
        list.add(css);
        list.add(javascrip);
        list.add(html);


        if (count == 0) {
            courseTypeRepository.saveAll(list);
            log.info("courseType database has been initialized");
        }
    }


    private void initCourse(){
        //tell if the course table is empty
        long count = courseRepository.count();
        if (count ==0){
            //fetch all the courseTypes
            List<CourseTypeEntity> courseTypes = courseTypeRepository.findAll();
            for (CourseTypeEntity type: courseTypes
            ) {
                //save all the courses into database and link to type
                createCoursesAndLinkToType(type);
            }

        }


    }

    private void createCoursesAndLinkToType(CourseTypeEntity courseType) {
        for (int i = 7; i < 13; i=i+2) {
            CourseEntity build = CourseEntity.builder().startDate(LocalDate.of(2024, i, 1)).duration("3 semesters").typeLinked(courseType).build();
            courseType.registerCourseUnderThisType(build);
            courseRepository.save(build);
        }
    }

    private Blob readImage(String imageName) {
        String basePath = "logos/";
        Resource resource = new ClassPathResource(basePath + imageName +".jpg");

        try (InputStream in = resource.getStream()) {
            byte[] bytes = in.readAllBytes();
            return new SerialBlob(bytes);
        } catch (Exception e) {
            log.warn("error when getting image data");
            return null;
        }


    }
}