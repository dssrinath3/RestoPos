package hiaccounts.in.restopos.application.bluetoothconnection;

import android.app.Activity;

import com.cie.btp.BtpConsts;
import com.cie.btp.CieBluetoothPrinter;
import com.cie.btp.PrinterWidth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;

public class PrintReceipt {
    private static final char NEWLINE = '\n';
    private static final String SPACE_SEPARATOR = " ";
    //if text has \n, \r or \t symbols it's better to split by \s+
    private static final String SPLIT_REGEXP= "";
    public static boolean printKot3Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename) {
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();

       // mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
       // mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_48MM);

        //mPrinter.resetPrinter();
       // mPrinter.setHighIntensity();
        mPrinter.setAlignmentCenter();
        //mPrinter.setBold();
        mPrinter.printTextLine("Kot Orders\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

        long qtyCount=0l;
        if (ordersList!=null && ordersList.size()>0){
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
            mPrinter.printTextLine("Item             Qty      Price\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

            for (Realm_TableOrders orders : ordersList)
            {
                mPrinter.printTextLine(orders.getItemName()+"       "+ orders.getQty()+"      "+orders.getUnitPrice()+"\n");
                qtyCount +=orders.getQty();
            }
        }

        mPrinter.printLineFeed();
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        mPrinter.printTextLine("  No.of items "+ qtyCount+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        mPrinter.printLineFeed();

        mPrinter.setAlignmentCenter();

        //Clearance for Paper tear
        mPrinter.printLineFeed();
        mPrinter.printLineFeed();

        //required only if the Batch Mode is set
        //print all commands
        mPrinter.batchPrint();

        return true;
    }
    public static boolean printKot2Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename) {
        mPrinter.printTextLine("\n");
        // mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
     //    mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_48MM);

        //mPrinter.resetPrinter();
        // mPrinter.setHighIntensity();
        mPrinter.setAlignmentCenter();
       // mPrinter.setBold();
        mPrinter.printTextLine("Kot Orders\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

        long qtyCount=0l;
        if (ordersList!=null && ordersList.size()>0){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
            Date date = new Date();
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
            mPrinter.printTextLine("Item             Qty      Price\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");


            for (Realm_TableOrders orders : ordersList)
            {
                String itemQty="",amountval="";
                itemQty = "" + orders.getQty();
                int amtSpaceStringLenght2 = 4-itemQty.length();
                for (int j=0;j<amtSpaceStringLenght2;j++){
                    itemQty=itemQty+" ";
                }

                amountval = String.format("%.2f", orders.getUnitPrice());
                int amtStringLenght2 = 4-amountval.length();
                for (int j=0;j<amtStringLenght2;j++){
                    amountval=" "+amountval;
                }

                String itemname="";
                itemname = orders.getItemName();
                if (itemname.length()>14){
                    itemname = breakLines(itemname,14,itemQty,amountval);

                    mPrinter.setAlignmentLeft();
                    mPrinter.printTextLine( itemname +  "\n");
                }else{
                    itemname=padRight(itemname, 14);
                    mPrinter.setAlignmentLeft();
                    mPrinter.printTextLine(itemname+"   "+ itemQty+"    "+amountval+"\n");
                }
                qtyCount +=orders.getQty();

            }
        }
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        mPrinter.printTextLine("No.of items "+ qtyCount+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        mPrinter.printLineFeed();

        mPrinter.setAlignmentCenter();

        //Clearance for Paper tear
        mPrinter.printLineFeed();
        mPrinter.printLineFeed();

        //required only if the Batch Mode is set
        //print all commands
        mPrinter.batchPrint();

        return true;
    }



    public static String breakLines(String input, int maxLineLength, String qty, String unitPrice) {
        String[] tokens = input.split(SPLIT_REGEXP);
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        boolean flag =true;
        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i];

            if (lineLen + (word).length() > maxLineLength) {
                if (i > 0) {
                    if (flag){
                        output.append("  ");
                        output.append(qty);
                        output.append("  ");
                        output.append(unitPrice);
                        output.append(NEWLINE);
                        flag =false;
                    }else {
                        output.append(NEWLINE);
                    }
                }
                lineLen = 0;
            }

            output.append(word);


            lineLen += word.length();
        }
        return output.toString();
    }

    public static boolean printCustomerPrint3Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();

        //mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
       // mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);

        //mPrinter.resetPrinter();
       // mPrinter.setHighIntensity();
        mPrinter.setAlignmentCenter();
       // mPrinter.setBold();
        mPrinter.printTextLine("Customer Bill\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

        String taxName="";
        long qtyCount=0l;
        Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
        if (ordersList!=null && ordersList.size()>0){
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
            mPrinter.setAlignmentLeft();

            String qtyHeader = "Qty";
            int qtyHeaderStringLenght = 4-qtyHeader.length();
            for (int j=0;j<qtyHeaderStringLenght;j++){
                qtyHeader=" "+qtyHeader;
            }
            String priceHeader = "Price";
            int priceSpaceStringLenght = 8-priceHeader.length();
            for (int j=0;j<priceSpaceStringLenght;j++){
                priceHeader=" "+priceHeader;
            }

            String amountHeader = "Amount";
            int amountSpaceStringLenght = 8-amountHeader.length();
            for (int j=0;j<amountSpaceStringLenght;j++){
                amountHeader=" "+amountHeader;
            }
            String itemHeader = "Item";
            String itemheader=padRight(itemHeader, 8);


            mPrinter.printTextLine(itemheader+" "+qtyHeader+" "+priceHeader+" "+amountHeader+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

            for (Realm_TableOrders orders : ordersList)
            {
                mPrinter.setAlignmentLeft();
                mPrinter.printTextLine(orders.getItemName()+" "+ orders.getQty()+" "+orders.getUnitPrice()+"  "+orders.getQty()*orders.getUnitPrice()+"\n");
                qtyCount +=orders.getQty();
                subTotal+=orders.getQty()*orders.getUnitPrice();
                taxAmount = orders.getTaxAmount();
                taxName =  orders.getTaxName();
            }
        }

        totalTaxAmount = (subTotal*taxAmount)/100;
        totalAmount = totalTaxAmount+subTotal;
        String totalAmountHeader = String.valueOf(totalAmount);
        int amountSpaceStringLenght = 8-totalAmountHeader.length();
        for (int j=0;j<amountSpaceStringLenght;j++){
            totalAmountHeader=" "+totalAmountHeader;
        }

        String subTotalHeader = String.valueOf(subTotal);
        int subTotalSpaceStringLenght = 8-subTotalHeader.length();
        for (int j=0;j<subTotalSpaceStringLenght;j++){
            subTotalHeader=" "+subTotalHeader;
        }

        String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
        int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
        for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
            totalTaxAmountHeader=" "+totalTaxAmountHeader;
        }

        String roundingValHeader = String.valueOf("0.00");
        int roundingValSpaceStringLenght = 8-roundingValHeader.length();
        for (int j=0;j<roundingValSpaceStringLenght;j++){
            roundingValHeader=" "+roundingValHeader;
        }
        String discountHeader = String.valueOf(discount);
        int discountSpaceStringLenght = 8-discountHeader.length();
        for (int j=0;j<discountSpaceStringLenght;j++){
            discountHeader=" "+discountHeader;
        }


        mPrinter.printLineFeed();
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

        mPrinter.setAlignmentLeft();
        String subTotal1 ="Sub Total:";
        String subTotalName=padRight(subTotal1, 40);
        mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
        if (taxName!=null && !taxName.equals("")){
            String tax =taxName+"("+taxAmount+"%)";
            String taxNameHeader=padRight(tax, 40);
            mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
        }

        String roundings ="Rounding Off";
        String roundingName=padRight(roundings, 40);
        mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
        String discountHeade ="Discount";
        String discountHeaderName=padRight(discountHeade, 40);
        mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        String totalHeader ="Total";
        String totalHeaderName=padRight(totalHeader, 40);;
        mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        mPrinter.printLineFeed();
        mPrinter.setAlignmentCenter();
        mPrinter.printLineFeed();
        mPrinter.printLineFeed();

        //required only if the Batch Mode is set
        //print all commands
        mPrinter.batchPrint();

        return true;
    }
    public static boolean printCustomerPrint2Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename) {
        mPrinter.printTextLine("\n");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();

        //mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
        // mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);

       // mPrinter.resetPrinter();
       // mPrinter.setHighIntensity();
        mPrinter.setAlignmentCenter();
       // mPrinter.setBold();
        mPrinter.printTextLine("Customer Bill\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

        String taxName="";
        long qtyCount=0l;
        Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
        if (ordersList!=null && ordersList.size()>0){
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
            mPrinter.setAlignmentLeft();

            String qtyHeader = "Qty";
            int qtyHeaderStringLenght = 4-qtyHeader.length();
            for (int j=0;j<qtyHeaderStringLenght;j++){
                qtyHeader=" "+qtyHeader;
            }
            String priceHeader = "Price";
            int priceSpaceStringLenght = 8-priceHeader.length();
            for (int j=0;j<priceSpaceStringLenght;j++){
                priceHeader=" "+priceHeader;
            }

            String amountHeader = "Amount";
            int amountSpaceStringLenght = 8-amountHeader.length();
            for (int j=0;j<amountSpaceStringLenght;j++){
                amountHeader=" "+amountHeader;
            }
            String itemHeader = "Item";
            String itemheader=padRight(itemHeader, 8);

            mPrinter.printTextLine(itemheader+"  "+qtyHeader+" "+priceHeader+" "+amountHeader+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

            for (Realm_TableOrders orders : ordersList)
            {
                String itemName="",itemQty="",priceval="",amountval="";

                if (orders.getItemName() != null)
                    itemName = orders.getItemName();

                int itemNameSpaceStringLenght = 0;
                for (int j=0;j<itemNameSpaceStringLenght;j++){
                    itemName=itemName+" ";
                }

                priceval = String.format("%.2f", orders.getUnitPrice());
                int amtSpaceStringLenght1=8-priceval.length();
                for (int j=0;j<amtSpaceStringLenght1;j++){
                    priceval=" "+priceval;
                }


                itemQty = "" + orders.getQty();
                int amtSpaceStringLenght2 = 4-itemQty.length();
                for (int j=0;j<amtSpaceStringLenght2;j++){
                    itemQty=itemQty+" ";
                }

                amountval = String.format("%.2f", orders.getQty()*orders.getUnitPrice());
                int amtStringLenght2 = 4-amountval.length();
                for (int j=0;j<amtStringLenght2;j++){
                    amountval=" "+amountval;
                }
                mPrinter.setAlignmentLeft();
                String itemname="";
                if (itemName.length()>11){
                    itemname = customerBreakLines(itemName,11,priceval,itemQty,amountval);
                    mPrinter.printTextLine( itemname +  "\n");
                }else{
                    itemname=padRight(itemName, 11);
                    mPrinter.printTextLine(itemname+" "+ itemQty+" "+priceval+" "+amountval+"\n");
                }



                qtyCount +=orders.getQty();
                subTotal+=orders.getQty()*orders.getUnitPrice();
                taxAmount = orders.getTaxAmount();
                taxName =  orders.getTaxName();
            }
        }

        totalTaxAmount = (subTotal*taxAmount)/100;
        totalAmount = totalTaxAmount+subTotal;
        String totalAmountHeader = String.valueOf(totalAmount);
        int amountSpaceStringLenght = 8-totalAmountHeader.length();
        for (int j=0;j<amountSpaceStringLenght;j++){
            totalAmountHeader=" "+totalAmountHeader;
        }

        String subTotalHeader = String.valueOf(subTotal);
        int subTotalSpaceStringLenght = 8-subTotalHeader.length();
        for (int j=0;j<subTotalSpaceStringLenght;j++){
            subTotalHeader=" "+subTotalHeader;
        }

        String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
        int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
        for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
            totalTaxAmountHeader=" "+totalTaxAmountHeader;
        }

        String roundingValHeader = String.valueOf("0.00");
        int roundingValSpaceStringLenght = 8-roundingValHeader.length();
        for (int j=0;j<roundingValSpaceStringLenght;j++){
            roundingValHeader=" "+roundingValHeader;
        }
        String discountHeader = String.valueOf(discount);
        int discountSpaceStringLenght = 8-discountHeader.length();
        for (int j=0;j<discountSpaceStringLenght;j++){
            discountHeader=" "+discountHeader;
        }
        mPrinter.printLineFeed();
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

        mPrinter.setAlignmentLeft();
        String subTotal1 ="Sub Total:";
        String subTotalName=padRight(subTotal1, 24);
        mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
        if (taxName!=null && !taxName.equals("")){
            String tax =taxName+"("+taxAmount+"%)";
            String taxNameHeader=padRight(tax, 24);
            mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
        }

        String roundings ="Rounding Off";
        String roundingName=padRight(roundings, 24);
        mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
        String discountNameHeader ="Discount";
        String discountHeaderName=padRight(discountNameHeader, 24);
        mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        String totalHeader ="Grand Total";
        String totalHeaderName=padRight(totalHeader, 24);
        mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        mPrinter.printLineFeed();
        mPrinter.setAlignmentCenter();
        mPrinter.printLineFeed();

        //print all commands
        mPrinter.batchPrint();

        return true;
    }
    public static String customerBreakLines(String input, int maxLineLength, String priceVal, String qty, String amountInc) {

        String[] tokens = input.split(SPLIT_REGEXP);
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        boolean flag =true;
        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i];

            if (lineLen + (word).length() > maxLineLength ) {
                if (i > 0) {
                    if (flag){
                        output.append(" ");
                        output.append(qty);
                        output.append(" ");
                        output.append(priceVal);
                        output.append(" ");
                        output.append(amountInc);
                        output.append(NEWLINE);
                        flag =false;
                    }else {
                        output.append(NEWLINE);

                    }
                }
                lineLen = 0;
            }
            output.append(word);
            lineLen += word.length();

        }
        return output.toString();
    }
    public static boolean printPaymentBillPrint3Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename, String orderType, String paymentType, Double roundingVal) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();
        Long tokenId = RealmManager.createRestaurentDao().getLastIndexCheckoutData();

        mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
        mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);

        mPrinter.resetPrinter();
        mPrinter.setHighIntensity();
        mPrinter.setAlignmentCenter();
        mPrinter.setBold();
        mPrinter.printTextLine("\nBill Number :BLN000"+tokenId+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

        String taxName="";
        long qtyCount=0l;
        Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
        if (ordersList!=null && ordersList.size()>0){
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(orderType+"\n");
            mPrinter.printTextLine(paymentType+"\n");
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
            mPrinter.setAlignmentLeft();

            String qtyHeader = "Qty";
            int qtyHeaderStringLenght = 4-qtyHeader.length();
            for (int j=0;j<qtyHeaderStringLenght;j++){
                qtyHeader=" "+qtyHeader;
            }
            String priceHeader = "Price";
            int priceSpaceStringLenght = 8-priceHeader.length();
            for (int j=0;j<priceSpaceStringLenght;j++){
                priceHeader=" "+priceHeader;
            }

            String amountHeader = "Amount";
            int amountSpaceStringLenght = 8-amountHeader.length();
            for (int j=0;j<amountSpaceStringLenght;j++){
                amountHeader=" "+amountHeader;
            }
            String itemHeader = "Item";
            String itemheader=padRight(itemHeader, 20);


            mPrinter.printTextLine(itemheader+"  "+qtyHeader+"  "+priceHeader+" "+amountHeader+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

            for (Realm_TableOrders orders : ordersList)
            {
                mPrinter.setAlignmentLeft();
                mPrinter.printTextLine(orders.getItemName()+"           "+ orders.getQty()+"     "+orders.getUnitPrice()+"  "+orders.getQty()*orders.getUnitPrice()+"\n");
                qtyCount +=orders.getQty();
                subTotal+=orders.getQty()*orders.getUnitPrice();
                taxAmount = orders.getTaxAmount();
                taxName =  orders.getTaxName();
            }
        }

        totalTaxAmount = (subTotal*taxAmount)/100;
        totalAmount = totalTaxAmount+subTotal;
        String totalAmountHeader = String.valueOf(totalAmount);
        int amountSpaceStringLenght = 8-totalAmountHeader.length();
        for (int j=0;j<amountSpaceStringLenght;j++){
            totalAmountHeader=" "+totalAmountHeader;
        }

        String subTotalHeader = String.valueOf(subTotal);
        int subTotalSpaceStringLenght = 8-subTotalHeader.length();
        for (int j=0;j<subTotalSpaceStringLenght;j++){
            subTotalHeader=" "+subTotalHeader;
        }

        String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
        int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
        for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
            totalTaxAmountHeader=" "+totalTaxAmountHeader;
        }

        String roundingValHeader = String.valueOf(roundingVal);
        int roundingValSpaceStringLenght = 8-roundingValHeader.length();
        for (int j=0;j<roundingValSpaceStringLenght;j++){
            roundingValHeader=" "+roundingValHeader;
        }
        String discountHeader = String.valueOf(discount);
        int discountSpaceStringLenght = 8-discountHeader.length();
        for (int j=0;j<discountSpaceStringLenght;j++){
            discountHeader=" "+discountHeader;
        }


        mPrinter.printLineFeed();
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

        mPrinter.setAlignmentLeft();
        String subTotal1 ="Sub Total:";
        String subTotalName=padRight(subTotal1, 40);
        mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
        if (taxName!=null && !taxName.equals("")){
            String tax =taxName+"("+taxAmount+"%)";
            String taxNameHeader=padRight(tax, 40);
            mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
        }

        String roundings ="Rounding Off";
        String roundingName=padRight(roundings, 40);
        mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
        String discountHeade ="Discount";
        String discountHeaderName=padRight(discountHeade, 40);
        mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        String totalHeader ="Total";
        String totalHeaderName=padRight(totalHeader, 40);;
        mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
        mPrinter.printLineFeed();
        mPrinter.setAlignmentCenter();
        mPrinter.printLineFeed();
        mPrinter.printLineFeed();

        //required only if the Batch Mode is set
        //print all commands
        mPrinter.batchPrint();

        return true;
    }
    public static boolean printPaymentBillPrint2Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename, String orderType, String paymentType, Double roundingVal) {
        mPrinter.printTextLine("\n");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();
        Long tokenId = RealmManager.createRestaurentDao().getLastIndexCheckoutData();

        mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
       // mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);

        //mPrinter.resetPrinter();
        //mPrinter.setHighIntensity();
        //mPrinter.setBold();
        //mPrinter.printLineFeed();
        mPrinter.setAlignmentCenter();
        mPrinter.printTextLine("Bill Number :BLN000"+tokenId+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

        String taxName="";
        long qtyCount=0l;
        Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
        if (ordersList!=null && ordersList.size()>0){
            mPrinter.setAlignmentCenter();
            mPrinter.printTextLine(orderType+"\n");
            mPrinter.printTextLine(paymentType+"\n");
            mPrinter.printTextLine(tablename+"\n");
            mPrinter.printTextLine(formatter.format(date)+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
            mPrinter.setAlignmentLeft();

            String qtyHeader = "Qty";
            int qtyHeaderStringLenght = 4-qtyHeader.length();
            for (int j=0;j<qtyHeaderStringLenght;j++){
                qtyHeader=" "+qtyHeader;
            }
            String priceHeader = "Price";
            int priceSpaceStringLenght = 8-priceHeader.length();
            for (int j=0;j<priceSpaceStringLenght;j++){
                priceHeader=" "+priceHeader;
            }

            String amountHeader = "Amount";
            int amountSpaceStringLenght = 8-amountHeader.length();
            for (int j=0;j<amountSpaceStringLenght;j++){
                amountHeader=" "+amountHeader;
            }
            String itemHeader = "Item";
            String itemheader=padRight(itemHeader, 10);


            mPrinter.printTextLine(itemheader+""+qtyHeader+" "+priceHeader+" "+amountHeader+"\n");
            mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

            for (Realm_TableOrders orders : ordersList)
            {
                String itemName="",itemQty="",priceval="",amountval="";

                if (orders.getItemName() != null)
                    itemName = orders.getItemName();

                int itemNameSpaceStringLenght = 0;
                for (int j=0;j<itemNameSpaceStringLenght;j++){
                    itemName=itemName+" ";
                }

                priceval = String.format("%.2f", orders.getUnitPrice());
                int amtSpaceStringLenght1=8-priceval.length();
                for (int j=0;j<amtSpaceStringLenght1;j++){
                    priceval=" "+priceval;
                }


                itemQty = "" + orders.getQty();
                int amtSpaceStringLenght2 = 4-itemQty.length();
                for (int j=0;j<amtSpaceStringLenght2;j++){
                    itemQty=itemQty+" ";
                }

                amountval = String.format("%.2f", orders.getQty()*orders.getUnitPrice());
                int amtStringLenght2 = 4-amountval.length();
                for (int j=0;j<amtStringLenght2;j++){
                    amountval=" "+amountval;
                }
                mPrinter.setAlignmentLeft();
                String itemname="";
                if (itemName.length()>11){
                    itemname = customerBreakLines(itemName,11,priceval,itemQty,amountval);
                    mPrinter.printTextLine( itemname +  "\n");
                }else{
                    itemname=padRight(itemName, 11);
                    mPrinter.printTextLine(itemname+" "+ itemQty+" "+priceval+" "+amountval+"\n");
                }



                qtyCount +=orders.getQty();
                subTotal+=orders.getQty()*orders.getUnitPrice();
                taxAmount = orders.getTaxAmount();
                taxName =  orders.getTaxName();
            }
        }

        totalTaxAmount = (subTotal*taxAmount)/100;
        totalAmount = totalTaxAmount+subTotal;
        String totalAmountHeader = String.valueOf(totalAmount);
        int amountSpaceStringLenght = 8-totalAmountHeader.length();
        for (int j=0;j<amountSpaceStringLenght;j++){
            totalAmountHeader=" "+totalAmountHeader;
        }

        String subTotalHeader = String.valueOf(subTotal);
        int subTotalSpaceStringLenght = 8-subTotalHeader.length();
        for (int j=0;j<subTotalSpaceStringLenght;j++){
            subTotalHeader=" "+subTotalHeader;
        }

        String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
        int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
        for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
            totalTaxAmountHeader=" "+totalTaxAmountHeader;
        }

        String roundingValHeader = String.valueOf(roundingVal);
        int roundingValSpaceStringLenght = 8-roundingValHeader.length();
        for (int j=0;j<roundingValSpaceStringLenght;j++){
            roundingValHeader=" "+roundingValHeader;
        }
        String discountHeader = String.valueOf(discount);
        int discountSpaceStringLenght = 8-discountHeader.length();
        for (int j=0;j<discountSpaceStringLenght;j++){
            discountHeader=" "+discountHeader;
        }
        mPrinter.printLineFeed();
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

        mPrinter.setAlignmentLeft();
        String subTotal1 ="Sub Total:";
        String subTotalName=padRight(subTotal1, 24);
        mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
        if (taxName!=null && !taxName.equals("")){
            String tax =taxName+"("+taxAmount+"%)";
            String taxNameHeader=padRight(tax, 24);
            mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
        }

        String roundings ="Rounding Off";
        String roundingName=padRight(roundings, 24);
        mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
        String discountNameHeader ="Discount";
        String discountHeaderName=padRight(discountNameHeader, 24);
        mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        String totalHeader ="Grand Total";
        String totalHeaderName=padRight(totalHeader, 24);
        mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
        mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
        mPrinter.printLineFeed();
        mPrinter.setAlignmentCenter();
        mPrinter.printLineFeed();

        //print all commands
        mPrinter.batchPrint();

        return true;
    }
    public static boolean printPaymentSplittBillPrint3Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename, String orderType, String paymentType, Double roundingVal, String splitAmt, int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();
        Long tokenId = RealmManager.createRestaurentDao().getLastIndexCheckoutData();

        mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
        mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);


                mPrinter.resetPrinter();
                mPrinter.setHighIntensity();
                mPrinter.setAlignmentCenter();
                mPrinter.setBold();
                mPrinter.printTextLine("\nBill Number :BLN000"+tokenId+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

                String taxName="";
                long qtyCount=0l;
                Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
                if (ordersList!=null && ordersList.size()>0){
                    mPrinter.setAlignmentCenter();
                    mPrinter.printTextLine(orderType+"\n");
                    mPrinter.printTextLine(paymentType+"\n");
                    mPrinter.printTextLine(tablename+"\n");
                    mPrinter.printTextLine(formatter.format(date)+"\n");
                    mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
                    mPrinter.setAlignmentLeft();

                    String qtyHeader = "Qty";
                    int qtyHeaderStringLenght = 4-qtyHeader.length();
                    for (int j=0;j<qtyHeaderStringLenght;j++){
                        qtyHeader=" "+qtyHeader;
                    }
                    String priceHeader = "Price";
                    int priceSpaceStringLenght = 8-priceHeader.length();
                    for (int j=0;j<priceSpaceStringLenght;j++){
                        priceHeader=" "+priceHeader;
                    }

                    String amountHeader = "Amount";
                    int amountSpaceStringLenght = 8-amountHeader.length();
                    for (int j=0;j<amountSpaceStringLenght;j++){
                        amountHeader=" "+amountHeader;
                    }
                    String itemHeader = "Item";
                    String itemheader=padRight(itemHeader, 20);


                    mPrinter.printTextLine(itemheader+"  "+qtyHeader+"  "+priceHeader+" "+amountHeader+"\n");
                    mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

                    for (Realm_TableOrders orders : ordersList)
                    {
                        mPrinter.setAlignmentLeft();
                        mPrinter.printTextLine(orders.getItemName()+"           "+ orders.getQty()+"     "+orders.getUnitPrice()+"  "+orders.getQty()*orders.getUnitPrice()+"\n");
                        //qtyCount +=orders.getQty();
                        subTotal+=orders.getQty()*orders.getUnitPrice();
                        taxAmount = orders.getTaxAmount();
                        taxName =  orders.getTaxName();
                        qtyCount = qtyCount+1;
                    }
                }

                totalTaxAmount = (subTotal*taxAmount)/100;
                totalAmount = totalTaxAmount+subTotal;
                String totalAmountHeader = String.valueOf(totalAmount);
                int amountSpaceStringLenght = 8-totalAmountHeader.length();
                for (int j=0;j<amountSpaceStringLenght;j++){
                    totalAmountHeader=" "+totalAmountHeader;
                }

                String subTotalHeader = String.valueOf(subTotal);
                int subTotalSpaceStringLenght = 8-subTotalHeader.length();
                for (int j=0;j<subTotalSpaceStringLenght;j++){
                    subTotalHeader=" "+subTotalHeader;
                }

                String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
                int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
                for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
                    totalTaxAmountHeader=" "+totalTaxAmountHeader;
                }

                String roundingValHeader = String.valueOf(roundingVal);
                int roundingValSpaceStringLenght = 8-roundingValHeader.length();
                for (int j=0;j<roundingValSpaceStringLenght;j++){
                    roundingValHeader=" "+roundingValHeader;
                }
                String discountHeader = String.valueOf(discount);
                int discountSpaceStringLenght = 8-discountHeader.length();
                for (int j=0;j<discountSpaceStringLenght;j++){
                    discountHeader=" "+discountHeader;
                }

                String splitAmount = "Rs "+splitAmt;
                int splitAmountSpaceStringLenght = 8-splitAmount.length();
                for (int j=0;j<splitAmountSpaceStringLenght;j++){
                    splitAmount=" "+splitAmount;
                }

                mPrinter.printLineFeed();
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");

                mPrinter.setAlignmentLeft();
                String subTotal1 ="Sub Total:";
                String subTotalName=padRight(subTotal1, 40);
                mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
                if (taxName!=null && !taxName.equals("")){
                    String tax =taxName+"("+taxAmount+"%)";
                    String taxNameHeader=padRight(tax, 40);
                    mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
                }

                String roundings ="Rounding Off";
                String roundingName=padRight(roundings, 40);
                mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
                String discountHeade ="Discount";
                String discountHeaderName=padRight(discountHeade, 40);
                mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
                String totalHeader ="Total";
                String totalHeaderName=padRight(totalHeader, 40);;
                mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
                i=i+1;
                String splitHeader ="Split "+i;
                String splitHeaderName=padRight(splitHeader, 40);
                mPrinter.printTextLine(splitHeaderName+ splitAmount+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line)+"\n");
                mPrinter.printTextLine("No of items  "+ qtyCount+"\n");
                mPrinter.printLineFeed();
                mPrinter.setAlignmentCenter();
                mPrinter.printLineFeed();
                mPrinter.printLineFeed();

                //required only if the Batch Mode is set
                //print all commands
                mPrinter.batchPrint();




        return true;
    }
    public static boolean printPaymentSplittBillPrint2Inch(Activity activity, CieBluetoothPrinter mPrinter, List<Realm_TableOrders> ordersList, String tablename, String orderType, String paymentType, Double roundingVal, String splitAmt, int i) {
        mPrinter.printTextLine("\n");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
        Date date = new Date();
        Long tokenId = RealmManager.createRestaurentDao().getLastIndexCheckoutData();

        mPrinter.setPrintMode(BtpConsts.PRINT_IN_BATCH);
        // mPrinter.setPrinterWidth(PrinterWidth.PRINT_WIDTH_72MM);

        //mPrinter.resetPrinter();
        //mPrinter.setHighIntensity();
        //mPrinter.setBold();
        //mPrinter.printLineFeed();

                mPrinter.setAlignmentCenter();
                mPrinter.printTextLine("Bill Number :BLN000"+tokenId+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

                String taxName="";
                long qtyCount=0l;
                Double subTotal=0.00,taxAmount=0.00,rounding=0.00,discount=0.00,totalTaxAmount=0.00,totalAmount=0.00;
                if (ordersList!=null && ordersList.size()>0){
                    mPrinter.setAlignmentCenter();
                    mPrinter.printTextLine(orderType+"\n");
                    mPrinter.printTextLine(paymentType+"\n");
                    mPrinter.printTextLine(tablename+"\n");
                    mPrinter.printTextLine(formatter.format(date)+"\n");
                    mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
                    mPrinter.setAlignmentLeft();

                    String qtyHeader = "Qty";
                    int qtyHeaderStringLenght = 4-qtyHeader.length();
                    for (int j=0;j<qtyHeaderStringLenght;j++){
                        qtyHeader=" "+qtyHeader;
                    }
                    String priceHeader = "Price";
                    int priceSpaceStringLenght = 8-priceHeader.length();
                    for (int j=0;j<priceSpaceStringLenght;j++){
                        priceHeader=" "+priceHeader;
                    }

                    String amountHeader = "Amount";
                    int amountSpaceStringLenght = 8-amountHeader.length();
                    for (int j=0;j<amountSpaceStringLenght;j++){
                        amountHeader=" "+amountHeader;
                    }
                    String itemHeader = "Item";
                    String itemheader=padRight(itemHeader, 10);


                    mPrinter.printTextLine(itemheader+""+qtyHeader+" "+priceHeader+" "+amountHeader+"\n");
                    mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

                    for (Realm_TableOrders orders : ordersList)
                    {
                        String itemName="",itemQty="",priceval="",amountval="";

                        if (orders.getItemName() != null)
                            itemName = orders.getItemName();

                        int itemNameSpaceStringLenght = 0;
                        for (int j=0;j<itemNameSpaceStringLenght;j++){
                            itemName=itemName+" ";
                        }

                        priceval = String.format("%.2f", orders.getUnitPrice());
                        int amtSpaceStringLenght1=8-priceval.length();
                        for (int j=0;j<amtSpaceStringLenght1;j++){
                            priceval=" "+priceval;
                        }


                        itemQty = "" + orders.getQty();
                        int amtSpaceStringLenght2 = 4-itemQty.length();
                        for (int j=0;j<amtSpaceStringLenght2;j++){
                            itemQty=itemQty+" ";
                        }

                        amountval = String.format("%.2f", orders.getQty()*orders.getUnitPrice());
                        int amtStringLenght2 = 4-amountval.length();
                        for (int j=0;j<amtStringLenght2;j++){
                            amountval=" "+amountval;
                        }
                        mPrinter.setAlignmentLeft();
                        String itemname="";
                        if (itemName.length()>11){
                            itemname = customerBreakLines(itemName,11,priceval,itemQty,amountval);
                            mPrinter.printTextLine( itemname +  "\n");
                        }else{
                            itemname=padRight(itemName, 11);
                            mPrinter.printTextLine(itemname+" "+ itemQty+" "+priceval+" "+amountval+"\n");
                        }



                        qtyCount +=orders.getQty();
                        subTotal+=orders.getQty()*orders.getUnitPrice();
                        taxAmount = orders.getTaxAmount();
                        taxName =  orders.getTaxName();
                        qtyCount = qtyCount+1;
                    }
                }

                totalTaxAmount = (subTotal*taxAmount)/100;
                totalAmount = totalTaxAmount+subTotal;
                String totalAmountHeader = String.valueOf(totalAmount);
                int amountSpaceStringLenght = 8-totalAmountHeader.length();
                for (int j=0;j<amountSpaceStringLenght;j++){
                    totalAmountHeader=" "+totalAmountHeader;
                }

                String subTotalHeader = String.valueOf(subTotal);
                int subTotalSpaceStringLenght = 8-subTotalHeader.length();
                for (int j=0;j<subTotalSpaceStringLenght;j++){
                    subTotalHeader=" "+subTotalHeader;
                }

                String totalTaxAmountHeader = String.valueOf(totalTaxAmount);
                int totalTaxAmountlSpaceStringLenght = 8-totalTaxAmountHeader.length();
                for (int j=0;j<totalTaxAmountlSpaceStringLenght;j++){
                    totalTaxAmountHeader=" "+totalTaxAmountHeader;
                }

                String roundingValHeader = String.valueOf(roundingVal);
                int roundingValSpaceStringLenght = 8-roundingValHeader.length();
                for (int j=0;j<roundingValSpaceStringLenght;j++){
                    roundingValHeader=" "+roundingValHeader;
                }
                String discountHeader = String.valueOf(discount);
                int discountSpaceStringLenght = 8-discountHeader.length();
                for (int j=0;j<discountSpaceStringLenght;j++){
                    discountHeader=" "+discountHeader;
                }
                String splitAmount = "Rs "+splitAmt;
                int splitAmountSpaceStringLenght = 8-splitAmount.length();
                for (int j=0;j<splitAmountSpaceStringLenght;j++){
                    splitAmount=" "+splitAmount;
                }
                mPrinter.printLineFeed();
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");

                mPrinter.setAlignmentLeft();
                String subTotal1 ="Sub Total:";
                String subTotalName=padRight(subTotal1, 24);
                mPrinter.printTextLine(subTotalName+ subTotalHeader+"\n");
                if (taxName!=null && !taxName.equals("")){
                    String tax =taxName+"("+taxAmount+"%)";
                    String taxNameHeader=padRight(tax, 24);
                    mPrinter.printTextLine(taxNameHeader+ totalTaxAmountHeader+"\n");
                }

                String roundings ="Rounding Off";
                String roundingName=padRight(roundings, 24);
                mPrinter.printTextLine(roundingName+ roundingValHeader+"\n");
                String discountNameHeader ="Discount";
                String discountHeaderName=padRight(discountNameHeader, 24);
                mPrinter.printTextLine(discountHeaderName+ discountHeader+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
                String totalHeader ="Grand Total";
                String totalHeaderName=padRight(totalHeader, 24);
                mPrinter.printTextLine(totalHeaderName+ totalAmountHeader+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
                i =i+1;
                String splitHeader ="Split "+i;
                String splitHeaderName=padRight(splitHeader, 24);
                mPrinter.printTextLine(splitHeaderName+ splitAmount+"\n");
                mPrinter.printTextLine(activity.getResources().getString(R.string.print_line2inch)+"\n");
                mPrinter.printTextLine("No of items  "+ qtyCount+"\n");


                mPrinter.printLineFeed();
                mPrinter.setAlignmentCenter();
                mPrinter.printLineFeed();
                mPrinter.batchPrint();





        return true;
    }

    public static String padRight(String s, int n) {
        return String.format("%0$-"+n+"s", s);
    }


}
