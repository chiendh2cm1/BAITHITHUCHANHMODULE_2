package com.codegym.View;

import com.codegym.Management.ContactManagement;
import com.codegym.Model.Contact;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final String PATH_CSV = "contact.csv";
    public static Scanner scanner = new Scanner(System.in);
    private static final ContactManagement contactManagement = new ContactManagement();
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_RESET = "\u001B[0m";

    public static void main(String[] args) {
        int choice;
        boolean flag = true;
        while (flag) {
            try {
                do {
                    //Đọc,Ghi file nhị phân tự động ; còn đọc ghi file CSV theo case 6,7
                    System.out.println();
                    System.out.println("\t\t\t\t\t\t\t ##******************** ỨNG DỤNG QUẢN LÝ DANH BẠ ĐIỆN THOẠI *******************##");
                    System.out.println("\t\t\t\t\t\t\t##                            1. HIỂN THỊ DANH BẠ                               ##");
                    System.out.println("\t\t\t\t\t\t\t##                            2. THÊM LIÊN HỆ MỚI                               ##");
                    System.out.println("\t\t\t\t\t\t\t##                            3. XÓA LIÊN HỆ                                    ##");
                    System.out.println("\t\t\t\t\t\t\t##                            4. CẬP NHẬP LIÊN HỆ                               ##");
                    System.out.println("\t\t\t\t\t\t\t##                            5. TÌM KIẾM LIÊN HỆ                               ##");
                    System.out.println("\t\t\t\t\t\t\t##                            6. ĐỌC TỪ FILE                                    ##");
                    System.out.println("\t\t\t\t\t\t\t##                            7. GHI VÀO FILE                                   ##");
                    System.out.println("\t\t\t\t\t\t\t##                            0. THOÁT CHƯƠNG TRÌNH                             ##");
                    System.out.print("\t\t\t\t\t\t\tNhập vào lựa chọn của bạn: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            showAllContact();
                            break;
                        case 2:
                            showCreatContact();
                            break;
                        case 3:
                            showDeleteContact();
                            break;
                        case 4:
                            showUpdateContact();
                            break;
                        case 5:
                            showFindContact();
                            break;
                        case 6:
                            showReadFileCSV();
                            break;
                        case 7:
                            showWriteFileCSV();
                            break;
                        case 0:
                            flag = false;
                            break;
                        default:
                            System.out.println(TEXT_RED + "\n\t\t\t\t\t\t\t------------------------> NHẬP TRONG KHOẢNG TỪ 0 ĐẾN 7 <--------------------------" + TEXT_RESET);
                            break;
                    }
                } while (choice != 0);
            } catch (Exception e) {
                System.out.println(TEXT_RED + "\n\t\t\t\t\t\t\t----------------------------> XIN VUI LÒNG NHẬP SỐ <------------------------------" + TEXT_RESET);
            }
        }
    }

    private static void showAllContact() {
        System.out.println("------Hiển thị danh bạ------");
        int size = contactManagement.size();
        if (size == 0) {
            System.out.println("Danh sách rỗng");
        } else {
            contactManagement.displayAll();
        }
    }

    private static void showCreatContact() {
        System.out.println("------Thêm liên hệ mới------");
        Contact contact = inputContac();
        contactManagement.addContact(contact);
    }

    private static void showDeleteContact() {
        System.out.println("------Xóa liên hệ ------");
        String phoneNumberDelete;
        System.out.println("Nhập vào số điện thoại cần xóa: ");
        phoneNumberDelete = scanner.nextLine();
        int choice1;
        System.out.println("1. Xác nhận xoá");
        System.out.println("0. Quay lại");
        System.out.print("Nhập vào lựa chọn của bạn: ");
        choice1 = Integer.parseInt(scanner.nextLine());
        if (choice1 == 1) {
            boolean isDeleted = contactManagement.deleteContact(phoneNumberDelete);
            if (isDeleted) {
                System.out.println("Xóa thành công");
            } else {
                System.out.println("Lỗi do SDT không tồn tại!!");
            }
        }
    }

    private static void showWriteFileCSV() {
        System.out.println("------ Ghi file CSV ------");
        contactManagement.writeFileCSV(contactManagement.getContactList(), PATH_CSV);
    }

    private static void showReadFileCSV() {
        System.out.println("------ Đọc file CSV ------");
        ArrayList<Contact> contactArrayList = contactManagement.readFileCSV(PATH_CSV);
        contactArrayList.forEach(System.out::println);
    }

    private static void showFindContact() {
        System.out.println("------Tìm kiếm danh bạ ------");
        System.out.println("Nhập vào số điện thoại của liên hệ cần tìm kiếm: ");
        String phoneNumber = scanner.nextLine();
        int index = contactManagement.findContactByPhoneNumber(phoneNumber);
        if (index != -1) {
            System.out.println(" Thông tin liên hệ cần tìm là:");
            System.out.println(contactManagement.getByPhoneNumber(phoneNumber));
        } else {
            System.out.println("Không tìm thấy danh bạ");
        }
    }

    private static void showUpdateContact() {
        System.out.println("------Cập nhập liên hệ mới ------");
        System.out.println("Nhập vào số điện thoại của liên hệ cần cập nhập: ");
        String phoneNumberUpdate = scanner.nextLine();
        int index = contactManagement.findContactByPhoneNumber(phoneNumberUpdate);
        if (index != -1) {
            Contact contact1 = inputContac();
            contactManagement.updateContact(phoneNumberUpdate, contact1);
        } else {
            System.out.println("Cập nhập bị lỗi do không tìm thấy SDT");
        }
    }

    private static Contact inputContac() {
        System.out.println("Nhập họ tên:");
        String name = scanner.nextLine();

        String phoneNumber;
        do {
            System.out.print("Nhập số điện thoại: ");
            System.out.println("<Gồm 10 số nếu có nhập số 0 ở đầu tiên. Nếu không nhập 0 thì còn 9 số>");
            phoneNumber = scanner.nextLine();
        } while (contactManagement.validatePhoneNumber(phoneNumber));

        System.out.println("Nhập tên nhóm:");
        String group = scanner.nextLine();

        String gender;
        do {
            System.out.println("Nhập giới tính:");
            System.out.println("<Chỉ được chọn MALE hoặc FEMALE>");
            gender = scanner.nextLine();
        } while (!gender.equals("MALE") && !gender.equals("FEMALE"));

        System.out.println("Nhập địa chỉ:");
        String address = scanner.nextLine();
        System.out.println("Nhập ngày sinh:");
        String dateOfBirth = scanner.nextLine();

        String email;
        do {
            System.out.print("Nhập email: ");
            System.out.println("<VD: abc@gmail.com>");
            email = scanner.nextLine();
        } while (contactManagement.validateEmail(email));
        return new Contact(phoneNumber, group, name, gender, address, dateOfBirth, email);
    }
}