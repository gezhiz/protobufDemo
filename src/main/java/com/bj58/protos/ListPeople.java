package com.bj58.protos;


import java.io.FileInputStream;

/**
 * Created by gezz on 2019/4/1.
 */
public class ListPeople {
    // Iterates though all people in the AddressBook and prints info about them.
    static void Print(AddressBookProtos.AddressBook addressBook) {
        for (AddressBookProtos.Person person: addressBook.getPeopleList()) {
            System.out.println("Person ID: " + person.getId());
            System.out.println("  Name: " + person.getName());
            if (person.getEmail() != null) {
                System.out.println("  E-mail address: " + person.getEmail());
            }

            for (AddressBookProtos.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("  Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print("  Home phone #: ");
                        break;
                    case WORK:
                        System.out.print("  Work phone #: ");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }

    // Main function:  Reads the entire address book from a file and prints all
    //   the information inside.
    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage:  ListPeople ADDRESS_BOOK_FILE");
//            System.exit(-1);
//        }
        String file = "/Users/gezz/logs/protobuf/addressbook.txt";
        // Read the existing address book.
        AddressBookProtos.AddressBook addressBook =
                AddressBookProtos.AddressBook.parseFrom(new FileInputStream(file));

        Print(addressBook);
    }
}
