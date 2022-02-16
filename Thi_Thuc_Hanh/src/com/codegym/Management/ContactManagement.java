package com.codegym.Management;

import com.codegym.IOFile.ReadFile;
import com.codegym.IOFile.WriteFile;
import com.codegym.Model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManagement implements WriteFile, ReadFile {
    private ArrayList<Contact> contactList = new ArrayList<>();
    private static final String PHONE_REGEX = "/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)$";// có dạng: abc@hotmail.com

    public int size() {
        return contactList.size();
    }

    public ContactManagement() {
        try {
            readFile("contact.txt");
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
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
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ");
        for (Contact contact : contactList) {
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", contact.getPhoneNumber(), contact.getGroup(), contact.getName(), contact.getGender(), contact.getAddress());
        }
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        try {
            writeFile("contact.txt");
        } catch (IOException e) {
        }
    }

    public void updateContact(String phoneNumber, Contact contact) {
        int index = findContactByPhoneNumber(phoneNumber);
        contactList.set(index, contact);
        try {
            writeFile("contact.txt");
        } catch (IOException e) {
        }
    }

    public boolean deleteContact(String phoneNumber) {
        int index = findContactByPhoneNumber(phoneNumber);
        if (index != -1) {
            contactList.remove(index);
            try {
                writeFile("contact.txt");
            } catch (IOException e) {
            }
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


    @Override
    public void readFile(String path) throws IOException, ClassNotFoundException {
        InputStream is = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(is);
        contactList = (ArrayList<Contact>) ois.readObject();
        is.close();
        ois.close();
    }

    @Override
    public void writeFile(String path) throws IOException {
        OutputStream os = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(contactList);
        os.close();
        oos.close();
    }
}
