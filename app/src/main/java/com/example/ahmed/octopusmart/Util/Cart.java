package com.example.ahmed.octopusmart.Util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ahmed.octopusmart.Model.CartProductItem;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ahmed on 02/12/2017.
 */

public class Cart {

    public static final String PREFERENCE_FILE_NAME = "p1";

    private static ArrayList<CartProductItem> productItems;

    public static long getTotalPrice(Context context){
        long price = 0;

        if(productItems == null || productItems.isEmpty()){
            productItems = getProducts(context);
        }

        if(productItems != null && !productItems.isEmpty()){
            for(CartProductItem productItem : productItems){
                price += productItem.getTotalPrice();
            }
        }

        return price;
    }

    public static boolean clearCart(Context context){
        // do not call on ui thread

        ObjectOutputStream out = null;

        productItems = new ArrayList<>();

        return save(context);
    }

    public static boolean addProduct(CartProductItem productItem, Context context){
        // do not call on ui thread

        Log.e("cart add", "adding to : " + productItems);

        ObjectOutputStream out = null;


        if(productItems == null){
            productItems = new ArrayList<>();
            productItems.add(productItem);
        }
        else{
            if(productItems.isEmpty()){
                productItems.add(productItem);
            }
            else{
                CartProductItem currentOrder = null;

                for(CartProductItem productOrderInfo : productItems){
                    if(productOrderInfo != null && Objects.equals(productOrderInfo.getId(), productItem.getId())){
                        currentOrder = productOrderInfo;
                        break;
                    }
                }

                if(currentOrder == null){
                    productItems.add(productItem);
                }

            }
        }


        return save(context);
    }

    public static boolean removeProduct(long productId, Context context){
        // do not call on ui thread

        Log.e("cart remove", "removing from : " + productItems);
        if(productItems != null && !productItems.isEmpty()){
            ObjectOutputStream out = null;

            if(context != null){

                long id = -1;
                CartProductItem productOrderInfo = null;
                for(int i = 0; i < productItems.size(); i++){
                    productOrderInfo = productItems.get(i);
                    if(productOrderInfo != null && productOrderInfo.getId() == productId){
                        id = productOrderInfo.getId();
                        break;
                    }
                }
                if(id != -1){
                    boolean b = productItems.remove(productOrderInfo);
                    if(b){
                        return save(context);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isProductCarted(long id){
        if(productItems != null && !productItems.isEmpty()){
            for(CartProductItem orderInfo : productItems){
                if(orderInfo != null && id == orderInfo.getId()){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean save(Context context){
        Log.e("cart save", "saving : " + productItems);
        if(context != null){

            ObjectOutputStream out = null;

            File f = new File(
                    context.getFilesDir(),
                    PREFERENCE_FILE_NAME
            );

            try {

                out = new ObjectOutputStream(new FileOutputStream(f));
                out.writeObject(productItems);
                out.close();

                return true;
            }

            catch (@NonNull IOException e){
                // ignore;
                Log.e("cart save", "error : " + e.getMessage());
            }
        }
        return false;
    }

    public static ArrayList<CartProductItem> getProducts(Context context){
        // do not call on ui thread

        if(productItems == null){

            productItems = new ArrayList<>();

            if(context != null){

                File f = new File(
                        context.getFilesDir(),
                        PREFERENCE_FILE_NAME
                );

                try {
                    FileInputStream inputFileStream = new FileInputStream(f);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
                    productItems = (ArrayList<CartProductItem>) objectInputStream.readObject();
                    objectInputStream.close();
                    inputFileStream.close();
                }

                catch (@NonNull IOException | ClassNotFoundException e){
                    // ignore;
                    productItems = new ArrayList<>();
                    Log.e("cart get", "error : " + e.getMessage());
                }
            }

        }

        Log.e("cart get", "retrieved " + productItems);

        return productItems;
    }

    public static void initCart(Context context){
        getProducts(context);
    }

    public static int getCartCount(){
        return productItems == null ? 0 : productItems.size();
    }

    public static void updateItem(CartProductItem cartProductItem, Context context){
        // do not call from ui thread
        productItems = getProducts(context);
        if(productItems != null && !productItems.isEmpty()){
            long id = cartProductItem.getId();

            if(id != -1){
                int index = -1;
                for(int i = 0; i < productItems.size(); i++){
                    CartProductItem item = productItems.get(i);
                    if(item.getId() == id){
                        index = i;
                        break;
                    }
                }

                if(index != -1){
                    productItems.set(index, cartProductItem );
                    save(context);
                }
            }
        }
    }
}