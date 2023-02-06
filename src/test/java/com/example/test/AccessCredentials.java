package com.example.test;

import com.example.test.TestCredentials;

public class AccessCredentials {
    public static void main(String[] args){
        TestCredentials test= new TestCredentials();
        System.out.println(test.first_user_psw());
        System.out.println(test.first_user_usr());
        System.out.println(test.second_user_psw());
        System.out.println(test.second_user_usr());
    }
}
