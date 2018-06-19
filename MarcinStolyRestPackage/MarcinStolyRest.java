package MarcinStolyRestPackage;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class MarcinStolyRest {

    OkHttpClient client = new OkHttpClient();
    String URL = "http://tosmansk.pythonanywhere.com/marcinstoly";
    //String URL = "http://127.0.0.1:5000/marcinstoly";
    Response getresponse;
    ArrayList<Response> postresponses = new ArrayList<Response>();
    List<List<String>> tabledata;
    ArrayList<HashMap<String, String>> resultdatapage = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> resultdatapage2 = new ArrayList<HashMap<String, String>>();


    @Given("^Log into page marcinstoly$")
    public void LogIntoPageMarcinstoly() throws Throwable {

        Request request = new Request.Builder()
                .url(this.URL)
                .build();
        this.getresponse = this.client.newCall(request).execute();
        String responcegetdata = this.getresponse.body().string();
        Document get = Jsoup.parse(responcegetdata);

        // Check the page was logged and assert input fields;

        MarcinStolyPage mainpage = new MarcinStolyPage(get);
        HashMap<String, String> pageelements = mainpage.returnvalues();

        assertEquals(pageelements.get("geth2"), "Obliczanie wymiarów stołu");
        assertEquals(pageelements.get("gettitle"), "Pomocnik stolarza");
        assertEquals(pageelements.get("select_henryk"), "Henryk");
        assertEquals(pageelements.get("select_denis"), "Denis");
        assertNotNull(pageelements.get("dl_input"));
        assertNotNull(pageelements.get("sz_input"));
        assertNotNull(pageelements.get("input_submit"));
    }

    @When("^Provide table data$")
    public void ProvideTableData(DataTable inputtable) throws Throwable {
    //send POST request and collect POST responses in the List
        this.tabledata = inputtable.raw();

        for (int y = 1 ; y < this.tabledata.size() ; y++ ) {

            //Populate DataTable data from feature
            String stol = this.tabledata.get(y).get(0);
            String dl = this.tabledata.get(y).get(1);
            String sz = this.tabledata.get(y).get(2);

            RequestBody formBody = new FormBody.Builder()
                        .add("stol_wybor", stol)
                        .add("dl", dl)
                        .add("sz", sz)
                        .build();
                Request request = new Request.Builder()
                        .url(this.URL)
                        .post(formBody)
                        .build();

        this.postresponses.add(this.client.newCall(request).execute());

        }
    }

    @Then("^Result page should be sent$")
    public void iShouldSeeResultPage() throws Throwable {

        int raw =1;
        String stol = "";
        String dlugosc = "";
        String szerokosc = "";

        for (Response postdata: this.postresponses) {

            stol = this.tabledata.get(raw).get(0);
            dlugosc = this.tabledata.get(raw).get(1);
            szerokosc = this.tabledata.get(raw).get(2);

            String responcepostdata = postdata.body().string();
            Document post = Jsoup.parse(responcepostdata);
            ResultPage results1 = new ResultPage(post);
            this.resultdatapage.add(results1.readtabledata(stol, dlugosc, szerokosc));

            //Check if the data is correct
            assertEquals(results1.checktitle(), "Wyniki obliczeń");
            assertTrue( results1.reth2().contains(stol) );
            assertTrue( results1.reth2().contains(dlugosc) );
            assertTrue( results1.reth2().contains(szerokosc) );
            raw++;
        }
    }

    @When("^Provide table with \"([^\"]*)\" with length \"([^\"]*)\" and width \"([^\"]*)\"$")
    public void provideTableWithWidthAndHeight(String in_stol, String in_dl, String in_sz) throws Throwable {

        this.resultdatapage.clear();

        RequestBody formBody2 = new FormBody.Builder()
                .add("stol_wybor", in_stol)
                .add("dl", in_dl)
                .add("sz",in_sz)
                .build();
        Request request2 = new Request.Builder()
                .url(this.URL)
                .post(formBody2)
                .build();

        Response postdata2 = client.newCall(request2).execute();
        String responcepostdata2 = postdata2.body().string();
        Document post2 = Jsoup.parse(responcepostdata2);
        ResultPage results2 = new ResultPage(post2);
        //ArryLis of HashMap objects
        this.resultdatapage2.add(results2.readtabledata(in_stol, in_dl, in_sz));
    }

    @Then("^Result page should contain \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\", \"([^\"]*)\"$")
    public void resultPageShouldContain(String stol, int dl, int sz, String rg_rw, String rg_rp,
                                        String oskwew_rw, String oskwew_rp, String oskzew_rw,
                                        String oskzew_rp, String armask_rw, String mask_rp) throws Throwable {

        /*
        *
        * These are values from feature file
        * "<stol>" "<dl>" "<sz>" "<rg_rw>" "<rg_rp>" "<oskwew_rw>" "<oskwew_rp>" "<oskzew_rw>" "<oskzew_rp>" "<mask_rw>"
        * "<mask_rp>"
        */

        //Assertation part checks all values are correct
        for (HashMap<String, String> data2: this.resultdatapage2)
            if ( data2.get("stol").equals("Henryk") &&  data2.get("dlugosc").equals(Integer.toString(dl)) &
                    data2.get("szerokosc").equals(Integer.toString(sz)) )  {

                assertEquals(data2.get("rg_rwzdl"), Double.toString(dl + 0.2 ));
                assertEquals(data2.get("rg_rpop"), Double.toString(sz - 20.0 ));
                assertEquals(data2.get("oskwew_rwzdl"), Double.toString(dl - 17.9 ));
                assertEquals(data2.get("oskwew_rpop"), Double.toString(sz - 20.0 ));
                assertEquals(data2.get("oskzew_rwzdl"), Double.toString(dl - 28.8 ));
                assertEquals(data2.get("oskzew_rpop"), Double.toString(sz - 28.8 ));
                assertEquals(data2.get("mask_rwzdl"), Double.toString(dl - 5.6 ));
                assertEquals(data2.get("mask_rpop"), Double.toString(sz - 11.6 ));
        }
            else if ( data2.get("stol").equals("Denis") &&  data2.get("dlugosc").equals(Integer.toString(dl)) &
                    data2.get("szerokosc").equals(Integer.toString(sz)) )  {

                assertEquals(data2.get("rg_rwzdl"), Double.toString(dl + 0.2 ));
                assertEquals(data2.get("rg_rpop"), Double.toString(sz - 16.0 ));
                assertEquals(data2.get("oskwew_rwzdl"), Double.toString(dl - 13.9 ));
                assertEquals(data2.get("oskwew_rpop"), Double.toString(sz - 16.0 ));
                assertEquals(data2.get("oskzew_rwzdl"), Double.toString(dl - 15.2 ));
                assertEquals(data2.get("oskzew_rpop"), Double.toString(sz - 15.2 ));
                assertEquals(data2.get("mask_rwzdl"), "-");
                assertEquals(data2.get("mask_rpop"), "-");
            }
    }
}
