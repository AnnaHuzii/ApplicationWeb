package com.example.applicationweb.controller;

import com.example.applicationweb.connection.StorageUser;
import com.example.applicationweb.creatio.client.UBMAppClient;
import com.example.applicationweb.creatio.file.UBMApplicationsFile;
import com.example.applicationweb.creatio.file.UBMApplicationsFileData;
import com.example.applicationweb.creatio.order.UsrApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class MenuController {
    private static final CreateDaoService creatioOrderDaoService = new CreateDaoService();

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView getMainMenu() {
        ModelAndView result = new ModelAndView("main-menu");
        return result;
    }

    @GetMapping("/order")
    public ModelAndView setChooseFormCommand(@RequestParam("form") String form) {
        StorageUser util = StorageUser.getInstance();
        String authorization = creatioOrderDaoService.authorization(util.getConnection());
        Map<String, String> districtsList = creatioOrderDaoService.districts();

        String messenger;
        if (authorization != null) {
            messenger = "Авторизація пройшла успішно";
        } else {
            messenger = "Щось пішло не так, не авторизувався в системі";
        }

        ModelAndView result = null;
        switch (form) {
            case "newOrder":
                result = new ModelAndView("order-new");
                result.addObject("Authorization", messenger);
                result.addObject("districts", districtsList);
                break;
            case "orderReceipt":
                result = new ModelAndView("order");
                result.addObject("Authorization", messenger);
                result.addObject("districts", districtsList);
                break;
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmation-order-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getOrderFile(Model model, HttpServletRequest request,
                               @RequestParam(value = "districtsValue") String districts,
                               @RequestParam("photo") MultipartFile photo,
                               @RequestParam("video") MultipartFile video) {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthDate = request.getParameter("birth_date");
        String phoneRequest = request.getParameter("phone");
        String street = request.getParameter("street");
        String houseNumber = request.getParameter("house-number");
        String numberPeople = request.getParameter("number-people");
        String phone = phoneRequest.replaceAll("\\D+", "");

        UsrApplication order = UsrApplication.builder().
                UBMAppDistrictId(districts).
                UBMAppSreet(street).
                UBMAppHousenumber(houseNumber).
                UBMAppNumberOfPeople(numberPeople).
                UBMAppStageId("e479f5ff-dcb3-4679-86c5-1b2300839008").
                UBMAppSourceId("cb6437df-877d-40a2-9c36-2bfa0f6d5661").
                build();

        UBMAppClient client = UBMAppClient.builder().
                Name(name + " " + surname).
                BirthDate(birthDate).
                TypeId("00783ef6-f36b-1410-a883-16d83cab0980").
                MobilePhone(phone).
                GivenName(name).
                MiddleName(surname).
                build();

        UBMApplicationsFile filePhoto;
        UBMApplicationsFile fileVideo;
        UBMApplicationsFileData data;
        try {

            filePhoto = UBMApplicationsFile.builder().
                    Name(photo.getOriginalFilename()).
                    TypeId("529bc2f8-0ee0-df11-971b-001d60e938c6").
                    Size(String.valueOf(photo.getInputStream().available())).
                    SysFileStorageId("65e42805-0e6d-43c9-8784-32b555f08421").
                    FileGroupId("efbf3a0d-d780-465a-8e4b-8c0765197cfb").
                    build();
            fileVideo = UBMApplicationsFile.builder().
                    Name(video.getOriginalFilename()).
                    TypeId("529bc2f8-0ee0-df11-971b-001d60e938c6").
                    Size(String.valueOf(video.getInputStream().available())).
                    SysFileStorageId("65e42805-0e6d-43c9-8784-32b555f08421").
                    FileGroupId("efbf3a0d-d780-465a-8e4b-8c0765197cfb").
                    build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String messenger = creatioOrderDaoService.createApplicationFile(order, client, filePhoto, fileVideo);

        model.addAttribute("reply", messenger);

        return "confirmation";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmation-order-new")
    public String getOrderNew(Model model, HttpServletRequest request,
                              @RequestParam(value = "districtsValue") String districts) {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birthDate = request.getParameter("birth_date");
        String phoneRequest = request.getParameter("phone");
        String street = request.getParameter("street");
        String houseNumber = request.getParameter("house-number");
        String numberPeople = request.getParameter("number-people");

        String phone = phoneRequest.replaceAll("\\D+", "");

        UsrApplication order = UsrApplication.builder().
                UBMAppDistrictId(districts).
                UBMAppSreet(street).
                UBMAppHousenumber(houseNumber).
                UBMAppNumberOfPeople(numberPeople).
                UBMAppStageId("97180019-59f8-4dbf-9d71-8cc51778018b").
                UBMAppSourceId("cb6437df-877d-40a2-9c36-2bfa0f6d5661").
                build();

        UBMAppClient client = UBMAppClient.builder().
                Name(name + " " + surname).
                BirthDate(birthDate).
                TypeId("00783ef6-f36b-1410-a883-16d83cab0980").
                MobilePhone(phone).
                GivenName(name).
                MiddleName(surname).
                UBMDistrictId(districts).
                Address(street).
                UBMHouseNumber(houseNumber).
                UBMContactNumberOfPeople(numberPeople).
                build();

        String messenger = creatioOrderDaoService.createApplicationNew(order, client);

        model.addAttribute("reply", messenger);

        return "confirmation";
    }
}
