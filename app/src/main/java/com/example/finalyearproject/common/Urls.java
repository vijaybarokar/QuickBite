package com.example.finalyearproject.common;

import java.net.URL;

public class Urls {
public static String webserviceAddress = "http://10.50.163.70";
    public static String webserviceAddressPort =  "/collegemanagementAPI/";
    public static String loginUserWebService =webserviceAddress + webserviceAddressPort + "userLogin.php";
    public static String RegisterUserWebService = webserviceAddress + webserviceAddressPort + "userdetailstbl.php";
    public static String forgetpasswordwebService =webserviceAddress + webserviceAddressPort + "userforgetpassword.php";
    public static String myprofile =webserviceAddress + webserviceAddressPort + "images/";
    public static String myDetailsWebService = webserviceAddress + webserviceAddressPort + "myDetails.php";
    public static String getAllcategoryDetailswebservice = webserviceAddress + webserviceAddressPort + "getAllCategoryDetails.php/";
    public static String getCategoryWiseDish = webserviceAddress + webserviceAddressPort + "categoryWiseDishes.php";
    public static String getImages = webserviceAddress + webserviceAddressPort + "images/";

    public static String UpdateprofileWebService = webserviceAddress +webserviceAddressPort + "updateprofile.php";


    public static String addUserRegisterImageWebService = webserviceAddress + webserviceAddressPort + "addUserRegisterImage.php";

//Admin API
    public static String getAllCustomerLocationWebService = webserviceAddress + webserviceAddressPort + "getAllCustomerLocation.php";


    public static String Rayorpay = "/verify_payment.php"; // your XAMPP IP


}
