package com.example.parcelabletest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//public class Book implements Serializable {
//    private String name;
//    private String price;
//
//    @Override
//    public String toString() {
//        return "Book{" +
//                "name='" + name + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }


    public class Book implements Parcelable {
        private String name;
        private String price;

        protected Book(Parcel in) {
            name = in.readString();
            price = in.readString();
        }

        public static final Creator<Book> CREATOR = new Creator<Book>() {
            @Override
            public Book createFromParcel(Parcel in) {
                return new Book(in);
            }

            @Override
            public Book[] newArray(int size) {
                return new Book[size];
            }
        };

        public Book(){

        }
        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(price);
        }
    }
