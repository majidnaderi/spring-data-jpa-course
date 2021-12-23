package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4)));


            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now()));


            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard(
                    "123456789",
                    student);

            student.setStudentIdCard(studentIdCard);

            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("fetch book lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(
                                    s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    });
//
            studentIdCardRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentRepository.deleteById(1L);

        };
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));
            studentRepository.save(student);
        }
    }
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
//        return args -> {
//            Student Majid = new Student(
//                    "Majid",
//                    "Naderi",
//                    "majidnaderi51@gmail.com",
//                    37);
////            studentRepository.save(student1);
//
//            Student Majid2 = new Student(
//                    "Majid",
//                    "Naderi",
//                    "majid2naderi51@gmail.com",
//                    39);
//
//            Student Ahmed = new Student(
//                    "Ahmed",
//                    "Ali",
//                    "ahmed.ali@amigoscode.edu",
//                    18
//            );
//
//            System.out.println("Adding maria and ahmed");
//            studentRepository.saveAll(List.of(Majid, Ahmed,Majid2));
//
//            System.out.print("Number of students: ");
//            System.out.println(studentRepository.count());
//
//            studentRepository
//                    .findStudentByEmail("majidnaderi51@gmail.com")
//                    .ifPresentOrElse(
//                            System.out::println,
//                            () -> System.out.println("Student with majid ahmed.ali@amigoscode.edu not found"));
//
//            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual(
//                    "Majid",
//                    37
//            ).forEach(System.out::println);
//
//
//            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
//                    "Maria",
//                    39
//            ).forEach(System.out::println);
//
//            System.out.println("Deleting Maria 2");
//            System.out.println(studentRepository.deleteStudentById(3L));
//
////            studentRepository
////                    .findById(2L)
////                    .ifPresentOrElse(
////                            System.out::println,
////                            ()->System.out.println("Student with ID 2 not found")
////                    );
////            studentRepository
////                    .findById(3L)
////                    .ifPresentOrElse(
////                            System.out::println,
////                            ()->System.out.println("Student with ID 3 not found")
////                    );
////
////            System.out.println("Select all students");
////            List<Student> students = studentRepository.findAll();
////            students.forEach(System.out::println);
////
////            System.out.println("Delete Majid");
////            studentRepository.deleteById(1L);
////
////            System.out.print("Number of students: ");
////            System.out.println(studentRepository.count());
//
//        };
//    }



}
