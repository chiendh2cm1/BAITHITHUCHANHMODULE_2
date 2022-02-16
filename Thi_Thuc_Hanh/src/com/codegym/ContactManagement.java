package com.codegym;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManagement {
    private static final List<Contact> contactList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static final String PHONE_REGEX = "/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/";//số điện thoại gồm 10 số nếu có nhập số 0 ở đầu tiên. Nếu không nhập 0 thì còn 9 số.
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)$";// có dạng: abc@hotmail.com
    public int size() {
        return contactList.size();
    }

    public int findContactByPhoneNumber(String phoneNumber) {
        int index = -1;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getPhoneNumber().equals(phoneNumber)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void displayAll() {
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n","Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ");
        for (Contact contact : contactList) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", contact.getPhoneNumber(), contact.getGroup(), contact.getName(), contact.getGender(), contact.getAddress());
        }
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    public void updateContact(String phoneNumber, Contact contact) {
        int index = findContactByPhoneNumber(phoneNumber);
        contactList.set(index, contact);
    }

    public boolean deleteContact(String phoneNumber) {
        int index = findContactByPhoneNumber(phoneNumber);
        if (index != -1) {
            contactList.remove(index);
            return true;
        }
        return false;
    }

    public Contact getByPhoneNumber(String phoneNumber) {
        int index = findContactByPhoneNumber(phoneNumber);
        return contactList.get(index);
    }

    public boolean validatePhoneNumber(String regex) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public boolean validateEmail(String regex) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public void writeFileCSV(ArrayList<Contact> contactList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (Contact contact : contactList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," + contact.getGender() + ","
                        + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println("Ghi file CSV thành công!");
        } catch (IOException ioe) {
        }
    }

    public ArrayList<Contact> readFileCSV(String path) {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                contacts.add(new Contact(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6]));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return contacts;
    }
}
