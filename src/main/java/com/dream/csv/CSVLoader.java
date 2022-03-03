package com.dream.csv;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 22-3-3
 * Time: 下午8:36
 */
public class CSVLoader extends CSVBaseListener {
    public static final String EMPTY = "";
    List<Map<String, String>> rows = new ArrayList<>();
    List<String> header;
    List<String> currentRow;

    @Override
    public void exitString(CSVParser.StringContext ctx) {
        currentRow.add(ctx.STRING().getText());
    }

    @Override
    public void exitText(CSVParser.TextContext ctx) {
        currentRow.add(ctx.TEXT().getText());
    }

    @Override
    public void exitEmpty(CSVParser.EmptyContext ctx) {
        currentRow.add(EMPTY);
    }

    @Override
    public void exitHdr(CSVParser.HdrContext ctx) {
        header = new ArrayList<>();
        header.addAll(currentRow);
    }

    @Override
    public void enterRow(CSVParser.RowContext ctx) {
        currentRow = new ArrayList<>();
    }

   @Override
   public void exitRow(CSVParser.RowContext ctx) {
       if(ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr){ // 如果当前行是标题行
           return;
       }
       Map<String, String> m = new LinkedHashMap<>();
       int i = 0;
       for(String v: currentRow) {
           m.put(header.get(i), v);
           i++;
       }
       rows.add(m);
   }
}
