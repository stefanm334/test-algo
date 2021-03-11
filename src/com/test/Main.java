package com.test;

import javax.sound.sampled.DataLine;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.Comparator;



public class Main {





    public static void main(String[] args) {
        String path = "C:\\Users\\DELL\\Desktop\\Navigation.csv";
        String line = "";
        String [][]Data = new String[10][10];
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(path));
            int j = 0;
            while ((line = bReader.readLine()) != null){
                String[] dataValues = line.split(";");
                for (int i = 0; i < dataValues.length; i++) {
                    Data[j][i] = dataValues[i];
                    if(i == dataValues.length -1) j = j + 1;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        class DataLine {
            public int Id;
            public String MenuName;
            public Integer ParentId;
            public boolean IsHidden;
            public String LinkURL;
                DataLine(int Id,String MenuName,Integer ParentId,boolean IsHidden,String LinkURL){
                    this.Id =Id;
                    this.MenuName = MenuName;
                    this.ParentId = ParentId;
                    this.IsHidden = IsHidden;
                    this.LinkURL = LinkURL;
                }
        }

       DataLine[] dataLineArrray = new DataLine[Data.length];
        for (int i = 1; i < Data.length ; i++) {

            int Id = Integer.parseInt(Data[i][0]);
            String MenuName = Data[i][1];
            Integer ParentId = 0;
            ParentId = Data[i][2].equals("NULL")?0:Integer.parseInt(Data[i][2]);
            boolean IsHidden = Boolean.parseBoolean(Data[i][3]);
            String LinkUrl = Data[i][4];
            dataLineArrray[i] = new DataLine(Id, MenuName,ParentId, IsHidden, LinkUrl );
        }


        class Menu{
            private int Id;
            private String MenuName;
            private Integer ParentId;
            private boolean IsHidden;
            private String LinkURL;
            private ArrayList<Menu> ChildrenList;
            Menu(int Id,String MenuName,Integer ParentId,boolean IsHidden,String LinkURL,ArrayList<Menu> ChildrenList){
                this.Id =Id;
                this.MenuName = MenuName;
                this.ParentId = ParentId;
                this.IsHidden = IsHidden;
                this.LinkURL = LinkURL;
                this.ChildrenList = ChildrenList;
            }
            public String getName()
            {
                return this.MenuName;
            }
        }

        Comparator<Menu> MenuNameComparator = new Comparator<Menu>() {

            public int compare(Menu s1, Menu s2) {
                String MenuName1 = s1.getName().toUpperCase();
                String MenuName2 = s2.getName().toUpperCase();
                return MenuName1.compareTo(MenuName2);
            }
        };

        ArrayList<Menu> levelOne = new ArrayList<Menu>();
        for (int i = 1; i < dataLineArrray.length ; i++) {
            if (dataLineArrray[i].ParentId == 0){
                levelOne.add(new Menu(dataLineArrray[i].Id,dataLineArrray[i].MenuName,
                        dataLineArrray[i].ParentId,
                        dataLineArrray[i].IsHidden,
                        dataLineArrray[i].LinkURL,
                        new ArrayList<Menu>()));
            }
        }
        levelOne.sort(MenuNameComparator);

        for (int i = 0; i < levelOne.size(); i++) {
//            System.out.println(levelOne.get(i).ChildrenList);
            for (int j = 1; j < dataLineArrray.length; j++) {
                if (levelOne.get(i).Id == dataLineArrray[j].ParentId) {
                    levelOne.get(i).ChildrenList.add(new Menu(dataLineArrray[j].Id,dataLineArrray[j].MenuName,
                            dataLineArrray[j].ParentId,
                            dataLineArrray[j].IsHidden,
                            dataLineArrray[j].LinkURL,
                            new ArrayList<Menu>()));
                }
            }
            levelOne.get(i).ChildrenList.sort(MenuNameComparator);

        }

        for (int i = 0; i < levelOne.size(); i++) {
            for (int j = 0; j < levelOne.get(i).ChildrenList.size(); j++) {
                for (int k = 1; k < dataLineArrray.length; k++) {
                    if (levelOne.get(i).ChildrenList.get(j).Id == dataLineArrray[k].ParentId){
                        levelOne.get(i).ChildrenList.get(j).ChildrenList.add(new Menu(dataLineArrray[k].Id,
                                dataLineArrray[k].MenuName,
                                dataLineArrray[k].ParentId,
                                dataLineArrray[k].IsHidden,
                                dataLineArrray[k].LinkURL,
                                new ArrayList<Menu>()));
                    }
                    levelOne.get(i).ChildrenList.get(j).ChildrenList.sort(MenuNameComparator);
                }
            }
        }

        for (int i = 0; i < levelOne.size(); i++) {
            String dots = ".";
            if (!levelOne.get(i).IsHidden) {
                System.out.println(dots + levelOne.get(i).MenuName);
            }
            for (int j = 0; j < levelOne.get(i).ChildrenList.size(); j++) {
                dots = "....";
                if (!levelOne.get(i).ChildrenList.get(j).IsHidden) {
                    System.out.println(dots + levelOne.get(i).ChildrenList.get(j).MenuName);
                    for (int k = 0; k < levelOne.get(i).ChildrenList.get(j).ChildrenList.size(); k++) {
                        if (!levelOne.get(i).ChildrenList.get(j).ChildrenList.get(k).IsHidden) {
                            dots= ".......";
                            System.out.println(dots + levelOne.get(i).ChildrenList.get(j).ChildrenList.get(k).MenuName);
                        }
                    }

                }
            }
        }



    }
}
