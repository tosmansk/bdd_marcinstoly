package MarcinStolyRestPackage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.HashMap;

public class MarcinStolyPage {

    Element geth2;
    Element gettitle;
    Element select_henryk;
    Element select_denis;
    Element dl_input;
    Element sz_input;
    Element input_submit;


    public MarcinStolyPage(Document getdata) {

        //Make returned page elements resolved;
        this.geth2 = getdata.selectFirst("h2");
        this.gettitle = getdata.selectFirst("title");
        this.select_henryk = getdata.selectFirst("option[value=\"Henryk\"]");
        this.select_denis = getdata.selectFirst("option[value=\"Denis\"]");
        this.dl_input = getdata.selectFirst("input[id=\"dl\"]");
        this.sz_input = getdata.selectFirst("input[id=\"sz\"]");
        this.input_submit = getdata.selectFirst("input[type=\"Submit\"]");
    }

        public HashMap<String, String> returnvalues() {
            HashMap<String, String> resolvedelements = new HashMap<String, String>();
            resolvedelements.put("geth2", this.geth2.text());
            resolvedelements.put("gettitle", this.gettitle.text());
            resolvedelements.put("select_henryk", this.select_henryk.text());
            resolvedelements.put("select_denis", this.select_denis.text());
            resolvedelements.put("dl_input", this.dl_input.text());
            resolvedelements.put("sz_input", this.sz_input.text());
            resolvedelements.put("input_submit", this.input_submit.text());
            return resolvedelements;
        }
}
