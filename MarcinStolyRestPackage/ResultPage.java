package MarcinStolyRestPackage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultPage {


    Element ramagorna_ramiakwzdlel;
    Element ramagorna_ramiakpopel;
    Element oskrzynawew_ramiakwzdlel;
    Element oskrzynawew_ramiakpopel;
    Element oskrzynazew_ramiakwzdlel;
    Element oskrzynazew_ramiakpopel;
    Element maskownica_ramiakwzdlel;
    Element maskownica_ramiakpopel;
    Element posttitle;
    Element posth2;

    public ResultPage(Document postdata) {


        this.posttitle = postdata.selectFirst("title");
        this.posth2 = postdata.selectFirst("h2");

        //resolve result`s elements from table
        this.ramagorna_ramiakwzdlel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(1)");
        this.ramagorna_ramiakpopel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(2)");
        this.oskrzynawew_ramiakwzdlel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(3)");
        this.oskrzynawew_ramiakpopel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(4)");
        this.oskrzynazew_ramiakwzdlel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(5)");
        this.oskrzynazew_ramiakpopel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(6)");
        this.maskownica_ramiakwzdlel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(7)");
        this.maskownica_ramiakpopel = postdata.selectFirst("table tbody tr:nth-of-type(3) td:nth-of-type(8)");

    }

    public HashMap<String, String> readtabledata(String stol, String dl, String sz) {
        //    this function populates table data

        //populate posttabeldata data
        HashMap<String, String> posttabeldata = new HashMap<String, String>();

        if (this.posth2.text().contains(stol)) posttabeldata.put("stol", stol);
        if (this.posth2.text().contains(dl)) posttabeldata.put("dlugosc", dl);
        if (this.posth2.text().contains(sz)) posttabeldata.put("szerokosc", sz);

        posttabeldata.put("rg_rwzdl", ramagorna_ramiakwzdlel.text());
        posttabeldata.put("rg_rpop", ramagorna_ramiakpopel.text());
        posttabeldata.put("oskwew_rwzdl", oskrzynawew_ramiakwzdlel.text());
        posttabeldata.put("oskwew_rpop", oskrzynawew_ramiakpopel.text());
        posttabeldata.put("oskzew_rwzdl", oskrzynazew_ramiakwzdlel.text());
        posttabeldata.put("oskzew_rpop", oskrzynazew_ramiakpopel.text());
        posttabeldata.put("mask_rwzdl", maskownica_ramiakwzdlel.text());
        posttabeldata.put("mask_rpop", maskownica_ramiakpopel.text());

        return posttabeldata;
    }

    public String checktitle() {
        return this.posttitle.text();
    }

    public String reth2() {
        return this.posth2.text();
    }




}
