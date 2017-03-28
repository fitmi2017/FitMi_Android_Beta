package com.db.modules;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.db.DatabaseHelper;
import com.fitmi.adapter.TotalFoodFooterAdapter;
import com.fitmi.dao.AllmealTypeDAO;
import com.fitmi.dao.CalenderFirsLastDay;
import com.fitmi.dao.FitmiFoodDAO;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.dao.MealTypeDAO;
import com.fitmi.dao.PlannerMealType;
import com.fitmi.utils.Constants;

import java.util.ArrayList;

public class FoodLoginModule extends BaseModule {


    public FoodLoginModule(Context ctx) {
        super(ctx);

        // TODO Auto-generated constructor stub
    }

    public FoodLoginModule getInstance(Context context) {
        return new FoodLoginModule(context);
    }

    ArrayList<FitmiFoodLogDAO> foodListData = new ArrayList<FitmiFoodLogDAO>();

    ArrayList<FitmiFoodLogDAO> allFoodListData = new ArrayList<FitmiFoodLogDAO>();

    ArrayList<PlannerMealType> plannerDate = new ArrayList<PlannerMealType>();

    public static void insertFitmifoodTable(FitmiFoodDAO fitmiFoodData, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.e("insertFitmifoodTable", "name " + fitmiFoodData.getItemName() + " Calories " + fitmiFoodData.getNfCalories() + " serving wt " + fitmiFoodData.getNfServingWeightGrams() + " Item Id " + fitmiFoodData.getItemId());
        if (Exists(fitmiFoodData.getItemId(), helper) == false) {
            String queryString = "INSERT INTO fitmi_food (" +
                    "item_title, cals, item_desc, serving_size, livestrong_id ) VALUES (" +
                    "'" + fitmiFoodData.getItemName() + "', '" + fitmiFoodData.getNfCalories() + "', '" + fitmiFoodData.getItemDescription() +
                    "', '" + fitmiFoodData.getNfServingWeightGrams() + "', '" + fitmiFoodData.getItemId() + "')";

            db.execSQL(queryString);

            db.close();
        }


    }

    public static void insertFitmifoodTable(String name, String calorie, String _id, String NfServingWeightGrams, String itemDesc, DatabaseHelper helper, Context ctx) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.e("insertFitmifoodTable", "name " + name + " Calories " + calorie + " serving wt " + NfServingWeightGrams + " Item Id " + _id);
        if (Exists(_id, helper) == false) {
            String queryString = "INSERT INTO fitmi_food (" +
                    "item_title, cals, item_desc, serving_size, livestrong_id ) VALUES (" +
                    "'" + name + "', '" + calorie + "', '" + itemDesc +
                    "', '" + NfServingWeightGrams + "', '" + _id + "')";

            if (db.isOpen()) {
                db.execSQL(queryString);
            } else {
                helper = new DatabaseHelper(ctx);
                db = helper.getWritableDatabase();
                db.execSQL(queryString);

            }

            db.close();
        }


    }

    public ArrayList<FitmiFoodLogDAO> selectFitMiFoodList(String livestrong_id, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        if (foodListData.size() > 0)
            foodListData.clear();


        String runQuery = "select * from fitmi_food where livestrong_id = '" + livestrong_id + "'";
        Cursor c = db.rawQuery(runQuery, null);
        //	new String[] { livestrong_id });


        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setCalory(c.getString(2));
                    fitmiFoodLogData.setMealWeight(c.getString(4));
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    foodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();
        return foodListData;
    }


    public static boolean Exists(String livestrong_id, DatabaseHelper helper) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from fitmi_food where livestrong_id = ?",
                new String[]{livestrong_id});
        boolean exists = false;
        if ((cursor.getCount() > 0)) {
            exists = true;
            cursor.close();
            db.close();
        } else {
            exists = false;
            cursor.close();
            db.close();
        }
        return exists;
    }

    public static boolean ExistsFoodLog(String reference_food_id, String meal_id, DatabaseHelper helper) {

        SQLiteDatabase db = helper.getWritableDatabase();
        String runQuery = "select * from fitmi_food_log where meal_id='" + meal_id + "' AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'" + "AND reference_food_id = '" + reference_food_id + "'";
        Cursor cursor = db.rawQuery(runQuery, null);
        //new String[] { reference_food_id });
        boolean exists = false;
        if ((cursor.getCount() > 0)) {
            exists = true;
            cursor.close();
            db.close();
        } else {
            exists = false;
            cursor.close();
            db.close();
        }
        return exists;
    }

    public static void insertFitmifoodLogTable(FitmiFoodLogDAO fitmiFoodLogData, DatabaseHelper helper) {

        SQLiteDatabase db = helper.getWritableDatabase();

        String queryString = "INSERT INTO fitmi_food_log (" +
                "user_id, user_profile_id, meal_id, food_name, description, cals,pro,car,fat,sod,cho, serving_size, reference_food_id, log_time,gm_calorie,isFavMeal, date_added )"
                +
                " VALUES (" +
                "'" + fitmiFoodLogData.getUserId() + "', '" + fitmiFoodLogData.getUserProfileId() + "', '" + fitmiFoodLogData.getMealId() +
                "', '" + fitmiFoodLogData.getFoodName() + "', '" + fitmiFoodLogData.getDescription() + "', '" +
                fitmiFoodLogData.getCalory() + "', '" + fitmiFoodLogData.getPro() + "', '" + fitmiFoodLogData.getCar() + "', '" +
                fitmiFoodLogData.getFat() + "', '" + fitmiFoodLogData.getSod() + "', '" + fitmiFoodLogData.getCho() + "', '" +
                fitmiFoodLogData.getServingSize() + "', '" + fitmiFoodLogData.getReferenceFoodId() + "', '" +
                fitmiFoodLogData.getLogTime() + "', '" + fitmiFoodLogData.getMealWeight() + "','" + fitmiFoodLogData.getMealFavourite() + "','" + fitmiFoodLogData.getDateAdded() + "')";

        db.execSQL(queryString);

        db.close();
    }

    public ArrayList<FitmiFoodLogDAO> selectFoodList(String mealId, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        if (foodListData.size() > 0)
            foodListData.clear();


        String queryString = "SELECT * FROM fitmi_food_log WHERE meal_id = ? AND user_profile_id='" + Constants.PROFILE_ID + "' AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";


        Cursor c = db.rawQuery(queryString, new String[]{mealId});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));

                    fitmiFoodLogData.setServingSize(c.getString(7));

                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    fitmiFoodLogData.setPro(c.getString(15));
                    fitmiFoodLogData.setCar(c.getString(16));
                    fitmiFoodLogData.setFat(c.getString(17));
                    fitmiFoodLogData.setSod(c.getString(18));
                    fitmiFoodLogData.setCho(c.getString(19));

                    foodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();
        return foodListData;
    }


    /**
     * Favourite section
     *
     * @return
     */
    public ArrayList<FitmiFoodLogDAO> selectAllFavFoodList() {
        //SQLiteDatabase db = helper.getWritableDatabase();

        if (foodListData.size() > 0)
            foodListData.clear();


        String queryString = "select * from fitmi_food_log where  user_profile_id='" + Constants.PROFILE_ID + "' and isFavorite=1";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setServingSize(c.getString(7));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    fitmiFoodLogData.setPro(c.getString(15));
                    fitmiFoodLogData.setCar(c.getString(16));
                    fitmiFoodLogData.setFat(c.getString(17));
                    fitmiFoodLogData.setSod(c.getString(18));
                    fitmiFoodLogData.setCho(c.getString(19));


                    foodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }
        c.close();

        return foodListData;
    }


    public ArrayList<FitmiFoodLogDAO> selectAllFavMealList(String mealid, String date) {

        if (allFoodListData.size() > 0)
            allFoodListData.clear();

        String queryString = "SELECT *  FROM fitmi_food_log where meal_id='" + mealid + "' and user_id='" + Constants.USER_ID + "'and user_profile_id='" + Constants.PROFILE_ID + "' and date_added between '" + date + " 00:00:01' AND '" + date + " 24:00:00' and isFavMeal=1";
        //String queryString = "SELECT *  FROM fitmi_food_log where user_id='"+Constants.USER_ID+"'and user_profile_id='"+Constants.PROFILE_ID+"' and date_added between '"+date+" 00:00:01' AND '"+date+" 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    String mealId = c.getString(3);

                    switch (Integer.parseInt(mealId)) {
                        case 1:

                            fitmiFoodLogData.setMealType("breakfast");
                            break;

                        case 2:
                            fitmiFoodLogData.setMealType("lunch");
                            break;
                        case 3:

                            fitmiFoodLogData.setMealType("dinner");
                            break;
                        case 4:

                            fitmiFoodLogData.setMealType("snack");
                            break;
                    }

                    allFoodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());
            }
        }

        c.close();


        return allFoodListData;

    }

    /**
     * end fav section
     *
     * @param foodId
     * @return
     */

    public ArrayList<FitmiFoodLogDAO> selectAllFavFoodListById(String foodId, String date)//,String date
    {
        //SQLiteDatabase db = helper.getWritableDatabase();

        if (foodListData.size() > 0)
            foodListData.clear();

        //String queryString = "select * from fitmi_food_log where meal_id = ? AND user_profile_id='"+Constants.PROFILE_ID+"'";
        String queryString = "select * from fitmi_food_log where meal_id = ? AND user_profile_id='" + Constants.PROFILE_ID + "' and isFavorite=1 and date_added between '" + date + " 00:00:01' AND '" + date + " 24:00:00' ";// and isFavorite=1 and date_added between '"+date+" 00:00:01' AND '"+date+" 24:00:00'

        Cursor c = db.rawQuery(queryString, new String[]{foodId});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setServingSize(c.getString(7));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    fitmiFoodLogData.setPro(c.getString(15));
                    fitmiFoodLogData.setCar(c.getString(16));
                    fitmiFoodLogData.setFat(c.getString(17));
                    fitmiFoodLogData.setSod(c.getString(18));
                    fitmiFoodLogData.setCho(c.getString(19));


                    foodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }
        c.close();

        return foodListData;
    }


    public ArrayList<FitmiFoodLogDAO> selectAllFoodListById(String foodId, String date) {
        //SQLiteDatabase db = helper.getWritableDatabase();

        if (foodListData.size() > 0)
            foodListData.clear();

        String queryString = "select * from fitmi_food_log where meal_id = ? AND user_profile_id='" + Constants.PROFILE_ID + "' and date_added between '" + date + " 00:00:01' AND '" + date + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{foodId});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setServingSize(c.getString(7));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    fitmiFoodLogData.setPro(c.getString(15));
                    fitmiFoodLogData.setCar(c.getString(16));
                    fitmiFoodLogData.setFat(c.getString(17));
                    fitmiFoodLogData.setSod(c.getString(18));
                    fitmiFoodLogData.setCho(c.getString(19));


                    foodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }
        c.close();

        return foodListData;
    }


    public ArrayList<FitmiFoodLogDAO> selectAllFoodList(DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        if (allFoodListData.size() > 0)
            allFoodListData.clear();


        String queryString = "SELECT * FROM fitmi_food_log WHERE user_id='" + Constants.USER_ID + "'and user_profile_id='" + Constants.PROFILE_ID + "' and date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[]{});

        //String queryString = "SELECT * FROM fitmi_food_log WHERE date_added between ? AND ?";

        //Cursor c = db.rawQuery(queryString, new String[] {Constants.conditionDate+" 00:00:01",Constants.conditionDate+" 23:59:59"});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    String mealId = c.getString(3);
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setServingSize(c.getString(7));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));


                    switch (Integer.parseInt(mealId)) {
                        case 1:

                            fitmiFoodLogData.setMealType("breakfast");
                            break;

                        case 2:
                            fitmiFoodLogData.setMealType("lunch");
                            break;
                        case 3:

                            fitmiFoodLogData.setMealType("dinner");
                            break;
                        case 4:

                            fitmiFoodLogData.setMealType("snack");
                            break;
                    }

                    fitmiFoodLogData.setPro(c.getString(15));
                    fitmiFoodLogData.setCar(c.getString(16));
                    fitmiFoodLogData.setFat(c.getString(17));
                    fitmiFoodLogData.setSod(c.getString(18));
                    fitmiFoodLogData.setCho(c.getString(19));


                    allFoodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());

            }
        }

		/*		FoodLoggingFragment foodListNotify = new FoodLoggingFragment();

		NotifyDatachangeDateselect callback = (NotifyDatachangeDateselect) foodListNotify;
		callback.notifyDataChange(foodAdapter, recentAdapter);*/

        c.close();

        db.close();
        return allFoodListData;
    }

    public static String getMealId(String mealName, DatabaseHelper helper) {
        String mealId = "";

        SQLiteDatabase db = helper.getWritableDatabase();

        String queryString = "SELECT * FROM meal_type WHERE meal_type_name = ?";

        Cursor c = db.rawQuery(queryString, new String[]{mealName});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                mealId = c.getString(0);
            }
        }
        c.close();
        db.close();
        return mealId;
    }


    public ArrayList<AllmealTypeDAO> getAllmealType(DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ArrayList<AllmealTypeDAO> list = new ArrayList<AllmealTypeDAO>();

        String queryString = "SELECT * FROM meal_type";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {


                do {
                    AllmealTypeDAO allMealType = new AllmealTypeDAO();

                    allMealType.setMealId(c.getString(0));
                    allMealType.setMealName(c.getString(1));

                    list.add(allMealType);

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return list;
    }

    public static void insertMealTypeTable(MealTypeDAO mealTypeData, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String queryString = "INSERT INTO meal_type (" +
                "meal_type_name, images_id, meal_type_is_default, active ) VALUES (" +
                "'" + mealTypeData.getMealTypeName() + "', '" + mealTypeData.getImageId() + "', '" + mealTypeData.getMealTypeDefault() +
                "', '" + mealTypeData.getActive() + "')";

        db.execSQL(queryString);

        db.close();
    }

    public static void editCalory(FitmiFoodLogDAO tagObject, DatabaseHelper helper, String calory) {
        SQLiteDatabase db = helper.getWritableDatabase();


        String[] args = {tagObject.getReferenceFoodId()};
        String query =
                "UPDATE fitmi_food_log SET cals =" + calory
                        + " WHERE reference_food_id =?";

        Cursor cu = db.rawQuery(query, args);
        cu.moveToFirst();
        cu.close();
        db.close();
    }

//    public static void editCaloryItew(String id, DatabaseHelper helper, String calory) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//
//        String[] args = {id};
//        String query =
//                "UPDATE fitmi_food_log SET cals =" + calory
//                        + " WHERE id =?";
//
//        Cursor cu = db.rawQuery(query, args);
//        cu.moveToFirst();
//        cu.close();
//        db.close();
//    }

    //added by avinash
    public static void editCaloryItew(String id, DatabaseHelper helper, String weight) {
        SQLiteDatabase db = helper.getWritableDatabase();
        float pergram, total;

        //String[] args = { id };
        String query = "UPDATE fitmi_food_log SET gm_calorie ='" + weight
                + "'  WHERE id ='" + id + "'";

//
//        switch (data_mode) {
//            case 0:
//                pergram = Float.parseFloat(dao.getCalory())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET cals ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//
//            case 1:
//                pergram = Float.parseFloat(dao.getPro())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET pro ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//
//            case 2:
//                pergram = Float.parseFloat(dao.getCar())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET car ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//
//            case 3:
//                pergram = Float.parseFloat(dao.getFat())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET fat ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//
//            case 4:
//                pergram = Float.parseFloat(dao.getSod())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET sod ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//
//            case 5:
//                pergram = Float.parseFloat(dao.getCho())
//                        / Float.parseFloat(serving_wt);
//                total = Float.parseFloat(weight)
//                        * pergram;
//                query = "UPDATE fitmi_food_log SET cho ='" + total
//                        + "', gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//            case 6:
//                query = "UPDATE fitmi_food_log SET gm_calorie ='" + weight
//                        + "'  WHERE id ='" + id + "'";
//
//                break;
//        }


        Cursor cu = db.rawQuery(query, null);
        cu.moveToFirst();
        cu.close();
        db.close();
    }


    public void updateFavourite(String foodId, String fav) {
        String[] args = {foodId, Constants.USER_ID, Constants.PROFILE_ID};
        String query =
                "UPDATE fitmi_food_log SET isFavorite =" + fav
                        + " WHERE id =? AND user_id=? AND user_profile_id = ?";

        Cursor cu = db.rawQuery(query, args);
        cu.moveToFirst();
        cu.close();
        db.close();
    }

    public void updateFavouriteMeal(String foodId, String fav) {
        String[] args = {foodId, Constants.USER_ID, Constants.PROFILE_ID};
        String query =
                "UPDATE fitmi_food_log SET isFavMeal =" + fav
                        + " WHERE id =? AND user_id=? AND user_profile_id = ?";

        Cursor cu = db.rawQuery(query, args);
        cu.moveToFirst();
        cu.close();

    }

    public static ArrayList<String> totalCalory(DatabaseHelper helper, Context ctx) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = null;

        ArrayList<String> calorySumList = new ArrayList<String>();
        if (db.isOpen()) {
            for (int i = 1; i <= 4; i++) {
                String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ? AND meal_id = ? AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";

                c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID, String.valueOf(i)});

                if (c.getCount() > 0) {
                    if (c.moveToFirst()) {

                        calorySumList.add(c.getString(0));
                    }
                }

            }
        } else {
            helper = new DatabaseHelper(ctx);
            db = helper.getWritableDatabase();
            for (int i = 1; i <= 4; i++) {
                String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ? AND meal_id = ? AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";

                c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID, String.valueOf(i)});

                if (c.getCount() > 0) {
                    if (c.moveToFirst()) {

                        calorySumList.add(c.getString(0));
                    }
                }

            }

        }
        c.close();
        db.close();

        return calorySumList;
    }


    public static String totalCaloryInList(DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = null;

        String calorySum = "";

        String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ?";

        c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                calorySum = c.getString(0);
            }
        }

        c.close();
        db.close();
        return calorySum;
    }


    public static String todayTotalCalory(String today, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        float calorySum = 0;

        //String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
        String queryString = "SELECT * FROM fitmi_food_log WHERE user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + today + " 00:00:01' AND '" + today + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

//        if (c.getCount() > 0) {
//            if (c.moveToFirst()) {
//
//
//                calorySum = c.getString(0);
//            }
//        }


        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    float pergram = Float.parseFloat(c.getString(6))
                            / Float.parseFloat(c.getString(7));
                    float cal = Float.parseFloat(c.getString(11))
                            * pergram;

                    calorySum += cal;

                } while (c.moveToNext());

            }
        }


        c.close();
        db.close();
        return String.valueOf(calorySum);
    }


    public static String todayTotalNutritionByMealid(String mealId,  DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        float calorySum = 0;
        String queryString = "SELECT * FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ? AND meal_id = ? AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID, mealId});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    float pergram=0;
                    switch (TotalFoodFooterAdapter.food_content_type) {

                        case "Cal":
                            pergram = Float.parseFloat(c.getString(6))
                                    / Float.parseFloat(c.getString(7));
                            break;

                        case "Pro":
                            pergram = Float.parseFloat(c.getString(15))
                                    / Float.parseFloat(c.getString(7));
                            break;

                        case "Car":
                            pergram = Float.parseFloat(c.getString(16))
                                    / Float.parseFloat(c.getString(7));
                            break;

                        case "Fat":
                            pergram = Float.parseFloat(c.getString(17))
                                    / Float.parseFloat(c.getString(7));
                            break;

                        case "Sod":
                            pergram = Float.parseFloat(c.getString(18))
                                    / Float.parseFloat(c.getString(7));
                            break;

                        case "Cho":
                            pergram = Float.parseFloat(c.getString(19))
                                    / Float.parseFloat(c.getString(7));
                            break;

                    }

                    float cal = Float.parseFloat(c.getString(11))
                            * pergram;

                    calorySum += cal;

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();
        return String.valueOf((int) calorySum);
    }


    public static String todayTotalCaloryByMealid(String mealId, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        float calorySum = 0;

//        //String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
//        String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ? AND meal_id = ? AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";
//        Cursor c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID, mealId});
//
//        if (c.getCount() > 0) {
//            if (c.moveToFirst()) {
//
//
//                calorySum = c.getString(0);
//            }
//        }


        String queryString = "SELECT * FROM fitmi_food_log WHERE user_id=? AND user_profile_id = ? AND meal_id = ? AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[]{Constants.USER_ID, Constants.PROFILE_ID, mealId});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    float pergram = Float.parseFloat(c.getString(6))
                            / Float.parseFloat(c.getString(7));
                    float cal = Float.parseFloat(c.getString(11))
                            * pergram;

                    calorySum += cal;

                } while (c.moveToNext());

            }
        }


        c.close();
        db.close();
        return String.valueOf((int) calorySum);
    }


    public ArrayList<PlannerMealType> plannerMealCheck(DatabaseHelper helper, String plannerMealId) {

        SQLiteDatabase db = helper.getWritableDatabase();

        if (plannerDate.size() > 0)
            plannerDate.clear();

        String queryString = "SELECT * FROM fitmi_food_log WHERE meal_id = '" + plannerMealId + "' AND user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {


                PlannerMealType plannerMealData = new PlannerMealType();

                plannerMealData.setMealId(c.getString(3));
                plannerMealData.setPlannerDate(c.getString(10));

                plannerDate.add(plannerMealData);
            }
        }
        c.close();
        db.close();
        return plannerDate;
    }

    public void deleteFoodLog(DatabaseHelper helper, String plannerMealId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        /*String queryString =  "delete FROM fitmi_food_log WHERE meal_id = '"+plannerMealId+"' AND user_id = '"+Constants.USER_ID+"' AND user_profile_id = '"+Constants.PROFILE_ID+"' AND date_added between '"+Constants.conditionDate+" 00:00:01' AND '"+Constants.conditionDate+" 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[] {});*/

        db.delete("fitmi_food_log", "meal_id = '" + plannerMealId + "' AND user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'",
                null);

    }

    public void deleteFood(String itemId) {

        db.delete("fitmi_food_log", "id = '" + itemId + "' AND user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + Constants.conditionDate + " 00:00:01' AND '" + Constants.conditionDate + " 24:00:00'",
                null);


    }

    public boolean selectPlannerWeekList(String firstLastDayObj, DatabaseHelper helper, String plannerMealId) {
        SQLiteDatabase db = helper.getWritableDatabase();


        String queryString = "SELECT * FROM fitmi_food_log WHERE meal_id = '" + plannerMealId + "' AND user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + firstLastDayObj + " 00:00:01' AND '" + firstLastDayObj + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {

            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public boolean selectPlannerMonthList(String firstLastDayObj, DatabaseHelper helper, String plannerMealId) {

        SQLiteDatabase db = helper.getWritableDatabase();

        String queryString = "SELECT * FROM fitmi_food_log WHERE meal_id = '" + plannerMealId + "' AND user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + firstLastDayObj + " 00:00:01' AND '" + firstLastDayObj + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }

    }


    public String totalCaloryDaily(String today, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String calorySum = "";

        //String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
        String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + today + " 00:00:01' AND '" + today + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {


                calorySum = c.getString(0);
            }
        }
        c.close();
        db.close();
        return calorySum;
    }

    public String totalCaloryWeekly(CalenderFirsLastDay firstLastDayObj, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String calorySum = "";

        //String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
        String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + firstLastDayObj.getFirstDay() + " 00:00:01' AND '" + firstLastDayObj.getLastDay() + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {


                calorySum = c.getString(0);
            }
        }
        c.close();
        db.close();
        return calorySum;
    }

    public String totalCaloryMonthly(CalenderFirsLastDay firstLastDayObj, DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String calorySum = "";

        //String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE date_added between '"+today+" 00:00:01' AND '"+today+" 24:00:00'";
        String queryString = "SELECT SUM(cals) FROM fitmi_food_log WHERE user_id = '" + Constants.USER_ID + "' AND user_profile_id = '" + Constants.PROFILE_ID + "' AND date_added between '" + firstLastDayObj.getFirstDay() + " 00:00:01' AND '" + firstLastDayObj.getLastDay() + " 24:00:00'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {


                calorySum = c.getString(0);
            }
        }
        c.close();
        db.close();
        return calorySum;
    }


    public void deleteFavFood(String mealId)//DatabaseHelper helper
    {

        db.delete("fitmi_food_log", "user_id = " + Constants.USER_ID + " AND user_profile_id = " + Constants.PROFILE_ID + " AND id = " + mealId,
                null);


    }


    /**
     * Recent food
     */

    public ArrayList<FitmiFoodLogDAO> selectRecentFoodList(DatabaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        if (allFoodListData.size() > 0)
            allFoodListData.clear();

        String queryString = "SELECT *  FROM fitmi_food_log where user_id='" + Constants.USER_ID + "'and user_profile_id='" + Constants.PROFILE_ID + "' group by reference_food_id order by log_time DESC LIMIT'" + 30 + "'";

        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    String mealId = c.getString(3);

                    switch (Integer.parseInt(mealId)) {
                        case 1:

                            fitmiFoodLogData.setMealType("breakfast");
                            break;

                        case 2:
                            fitmiFoodLogData.setMealType("lunch");
                            break;
                        case 3:

                            fitmiFoodLogData.setMealType("dinner");
                            break;
                        case 4:

                            fitmiFoodLogData.setMealType("snack");
                            break;
                    }

                    allFoodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());
            }
        }

        c.close();
        db.close();
        return allFoodListData;
    }

    /**
     * recent meal section
     *
     * @return
     */

    public ArrayList<String> selectRecentMealDate() {
        ArrayList<String> dateList = new ArrayList<String>();

        String queryString = "SELECT log_time  FROM fitmi_food_log where user_id='" + Constants.USER_ID + "'and user_profile_id='" + Constants.PROFILE_ID + "' GROUP BY DATE(log_time) ORDER BY log_time DESC LIMIT 5";
        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {

            if (c.moveToFirst()) {

                do {

                    dateList.add(c.getString(0));

                } while (c.moveToNext());
            }
        }
        c.close();

        return dateList;
    }


    public ArrayList<FitmiFoodLogDAO> selectRecentMealList(String mealid, String date) {

        if (allFoodListData.size() > 0)
            allFoodListData.clear();

        String queryString = "SELECT *  FROM fitmi_food_log where meal_id='" + mealid + "' and user_id='" + Constants.USER_ID + "'and user_profile_id='" + Constants.PROFILE_ID + "' and date_added between '" + date + " 00:00:01' AND '" + date + " 24:00:00'";
        //String queryString = "SELECT *  FROM fitmi_food_log where user_id='"+Constants.USER_ID+"'and user_profile_id='"+Constants.PROFILE_ID+"' and date_added between '"+date+" 00:00:01' AND '"+date+" 24:00:00'";
        Cursor c = db.rawQuery(queryString, new String[]{});

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {

                do {
                    FitmiFoodLogDAO fitmiFoodLogData = new FitmiFoodLogDAO();
                    fitmiFoodLogData.setFoodLogId(c.getString(0));
                    fitmiFoodLogData.setFoodName(c.getString(4));
                    fitmiFoodLogData.setDescription(c.getString(5));
                    fitmiFoodLogData.setCalory(c.getString(6));
                    fitmiFoodLogData.setReferenceFoodId(c.getString(8));
                    fitmiFoodLogData.setMealWeight(c.getString(11));
                    fitmiFoodLogData.setFavourite(c.getString(12));
                    fitmiFoodLogData.setMealFavourite(c.getString(13));

                    String mealId = c.getString(3);

                    switch (Integer.parseInt(mealId)) {
                        case 1:

                            fitmiFoodLogData.setMealType("breakfast");
                            break;

                        case 2:
                            fitmiFoodLogData.setMealType("lunch");
                            break;
                        case 3:

                            fitmiFoodLogData.setMealType("dinner");
                            break;
                        case 4:

                            fitmiFoodLogData.setMealType("snack");
                            break;
                    }

                    allFoodListData.add(fitmiFoodLogData);
                } while (c.moveToNext());
            }
        }

        c.close();

        return allFoodListData;
    }
}
