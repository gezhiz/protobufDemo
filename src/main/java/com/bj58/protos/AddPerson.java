package com.bj58.protos;

import java.io.*;

/**
 * Created by gezz on 2019/4/1.
 */
public class AddPerson {
    static AddressBookProtos.Person PromptForAddress(BufferedReader stdin,
                                                     PrintStream stdout) throws IOException {
        AddressBookProtos.Person.Builder person = AddressBookProtos.Person.newBuilder();

        stdout.print("Enter person ID: ");
        person.setId(Integer.valueOf(stdin.readLine()));

        stdout.print("Enter name: ");
        person.setName(stdin.readLine());

        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if (email.length() > 0) {
            person.setEmail(email);
        }

        while (true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if (number.length() == 0) {
                break;
            }

            AddressBookProtos.PhoneNumber.Builder phoneNumber =
                    AddressBookProtos.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            if (type.equals("mobile")) {
                phoneNumber.setType(AddressBookProtos.PhoneType.MOBILE);
            } else if (type.equals("home")) {
                phoneNumber.setType(AddressBookProtos.PhoneType.HOME);
            } else if (type.equals("work")) {
                phoneNumber.setType(AddressBookProtos.PhoneType.WORK);
            } else {
                stdout.println("Unknown phone type.  Using default.");
            }

            person.addPhones(phoneNumber);
        }

        return person.build();
    }
    // Main function:  Reads the entire address book from a file,
    //   adds one person based on user input, then writes it back out to the same
    //   file.
    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage:  AddPerson ADDRESS_BOOK_FILE");
//            System.exit(-1);
//        }

        String file = "/Users/gezz/logs/protobuf/addressbook.txt";
        AddressBookProtos.AddressBook.Builder addressBook = AddressBookProtos.AddressBook.newBuilder();

        // Read the existing address book.
        try {
            addressBook.mergeFrom(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(file + ": File not found.  Creating a new file.");
        }

        // Add an address.
        addressBook.addPeople(
                PromptForAddress(new BufferedReader(new InputStreamReader(System.in)),
                        System.out));

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(file);
        addressBook.build().writeTo(output);
        output.close();
    }
}
