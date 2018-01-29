package test;

import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;
import sniffer.ParseResult;
import sniffer.ParserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class ParserTest {

    private static final String apoteaCampaignPage =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"sv\">\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "                        <div id=\"promotion-articles\">\n" +
                    "                            <div class=\"products5\">    \n" +
                    "                                <ul>            \n" +
                    "                                    <li class=\"separator\"></li>            \n" +
                    "                                    <li class=\"\">                \n" +
                    "                                        <div class=\"img center\">                    \n" +
                    "                                            <a href=\"/aco-spotless-daily-face-wash-200-ml\">                        \n" +
                    "                                                <img src=\"http://media.apotea.se/product-images/MS/5901-1.jpg\" alt=\"ACO Spotless Daily Face Wash 200 ml\" />                    \n" +
                    "                                            </a>                \n" +
                    "                                        </div>      \n" +
                    "                                        \n" +
                    "                                        <div style=\"position: relative;\">                    \n" +
                    "                                            <div class=\"price-tag\" onclick=\"document.location='/aco-spotless-daily-face-wash-200-ml'\">                            \n" +
                    "                                                <a href=\"/aco-spotless-daily-face-wash-200-ml\">\n" +
                    "                                                    <span class=\"\">33 kr</span>\n" +
                    "                                                </a>                    \n" +
                    "                                            </div>                    \n" +
                    "                                            <div class=\"badges\">                            \n" +
                    "                                                <div class=\"promotion-badge\">\n" +
                    "                                                    <a href=\"/aco-spotless-daily-face-wash-200-ml\">\n" +
                    "                                                        <img src=\"/content/images/6/kampanj_kampanj.svg\" onerror=\"this.onerror = null; this.src = '/content/images/6/kampanj_kampanj.png'\" />\n" +
                    "                                                    </a>\n" +
                    "                                                </div>                                            \n" +
                    "                                            </div>                                    \n" +
                    "                                        </div>                \n" +
                    "                                        <div id=\"list-23899\">                    \n" +
                    "                                            <div class=\"is-med-div\"></div>                    \n" +
                    "                                            <div class=\"name\" style=\"overflow:hidden;height:32px;\">\n" +
                    "                                                <a href=\"/aco-spotless-daily-face-wash-200-ml\">ACO Spotless Daily Face Wash 200 ml</a>\n" +
                    "                                            </div>                    \n" +
                    "                                            <div class=\"list-box\">                                \n" +
                    "                                                <div class=\"store-price\">Vårt ord.pris: 42 kr</div>                            \n" +
                    "                                                <img class=\"list-add\" data-sku=\"23899\" src=\"/content/images/6/add-to-cart.png\" alt=\"Lägg 1 st ACO Spotless Daily Face Wash 200 ml i varukorgen\" />                        \n" +
                    "                                                <div style=\"clear:both;\"></div>                    \n" +
                    "                                            </div>                \n" +
                    "                                        </div>            \n" +
                    "                                    </li>            \n" +
                    "                                    \n" +
                    "                                    <li class=\"\">                \n" +
                    "                                        <div class=\"img center\">                    \n" +
                    "                                            <a href=\"/aco-spotless-daily-purifying-toner-200-ml\">                        \n" +
                    "                                                <img src=\"http://media.apotea.se/product-images/MS/5899-1.jpg\" alt=\"ACO Spotless Daily Purifying Toner 200 ml\" />                    \n" +
                    "                                            </a>                \n" +
                    "                                        </div>                \n" +
                    "                                        <div style=\"position: relative;\">                    \n" +
                    "                                            <div class=\"price-tag\" onclick=\"document.location='/aco-spotless-daily-purifying-toner-200-ml'\">                            \n" +
                    "                                                <a href=\"/aco-spotless-daily-purifying-toner-200-ml\"><span class=\"\">27 kr</span></a>                    \n" +
                    "                                            </div>                    \n" +
                    "                                            <div class=\"badges\">                            \n" +
                    "                                                <div class=\"promotion-badge\"><a href=\"/aco-spotless-daily-purifying-toner-200-ml\">\n" +
                    "                                                    <img src=\"/content/images/6/kampanj_kampanj.svg\" onerror=\"this.onerror = null; this.src = '/content/images/6/kampanj_kampanj.png'\" /></a>\n" +
                    "                                                </div>                                            \n" +
                    "                                            </div>                                    \n" +
                    "                                        </div>                \n" +
                    "                                        <div id=\"list-23897\">                    \n" +
                    "                                            <div class=\"is-med-div\"></div>                    \n" +
                    "                                            <div class=\"name\" style=\"overflow:hidden;height:32px;\">\n" +
                    "                                                <a href=\"/aco-spotless-daily-purifying-toner-200-ml\">ACO Spotless Daily Purifying Toner 200 ml</a>\n" +
                    "                                            </div>                    \n" +
                    "                                            <div class=\"list-box\">                                \n" +
                    "                                                <div class=\"store-price\">Vårt ord.pris: 34 kr</div>                            \n" +
                    "                                                <img class=\"list-add\" data-sku=\"23897\" src=\"/content/images/6/add-to-cart.png\" alt=\"Lägg 1 st ACO Spotless Daily Purifying Toner 200 ml i varukorgen\" />                        \n" +
                    "                                                <div style=\"clear:both;\"></div>                    \n" +
                    "                                            </div>                \n" +
                    "                                        </div>            \n" +
                    "                                    </li>            \n" +
                    "                                    \n" +
                    "                                    \n" +
                    "                                </ul>\n" +
                    "                                \n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";

    private static final String apoteaCampaignPage2 =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"sv\">\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<body>\n" +
                    "    <div id=\"promotion-articles\">\n" +
                    "    <ul>\n" +
                    "        <li class=\"\">                \n" +
                    "            <div class=\"img center\">                    \n" +
                    "                <a href=\"/anthon-berg-dark-milk-fairtrade-100-g\">                        \n" +
                    "                    <img src=\"http://media.apotea.se/product-images/MS/anthon-berg-dark-milk-fairtrade-100-g-0.jpg\" alt=\"Anthon Berg Dark Milk Fairtrade 100 g\" />                    \n" +
                    "                </a>                \n" +
                    "            </div>  \n" +
                    "            \n" +
                    "            <div style=\"position: relative;\">                   \n" +
                    "           \n" +
                    "                <div class=\"price-tag\" onclick=\"document.location='/anthon-berg-dark-milk-fairtrade-100-g'\">                            \n" +
                    "                    <a href=\"/anthon-berg-dark-milk-fairtrade-100-g\">\n" +
                    "                        <span class=\"\">1.299 kr</span>\n" +
                    "                    </a>                    \n" +
                    "                </div>                    \n" +
                    "                <div class=\"badges\">                            \n" +
                    "                    <div class=\"promotion-badge\"><a href=\"/anthon-berg-dark-milk-fairtrade-100-g\">\n" +
                    "                        <img src=\"/content/images/6/kampanj_kortdatum.svg\" onerror=\"this.onerror = null; this.src = '/content/images/6/kampanj_kortdatum.png'\" />\n" +
                    "                    </a>\n" +
                    "                    </div>                                            \n" +
                    "                </div>                                    \n" +
                    "            </div>\n" +
                    "            \n" +
                    "            <div id=\"list-147416\">                    \n" +
                    "                <div class=\"is-med-div\"></div>                    \n" +
                    "                <div class=\"name\" style=\"overflow:hidden;height:32px;\">\n" +
                    "                    <a href=\"/anthon-berg-dark-milk-fairtrade-100-g\">Anthon Berg Dark Milk Fairtrade 100 g</a></div>                    \n" +
                    "                <div class=\"list-box\">                                \n" +
                    "                    <div class=\"store-price\">Vårt ord.pris: 1.399 kr</div>                            \n" +
                    "                    <img class=\"list-add\" data-sku=\"147416\" src=\"/content/images/6/add-to-cart.png\" alt=\"Lägg 1 st Anthon Berg Dark Milk Fairtrade 100 g i varukorgen\" />                       \n" +
                    "                    <div style=\"clear:both;\"></div>                    \n" +
                    "                </div>                \n" +
                    "            </div>            \n" +
                    "        </li>            \n" +
                    "        <li class=\"separator\"></li>            \n" +
                    "        <li class=\"\">                \n" +
                    "            <div class=\"img center\">                    \n" +
                    "                <a href=\"/bluetouch-rem-f&#246;r-nedre-delen-av-ryggen-tillbeh&#246;r\">                        \n" +
                    "                    <img src=\"http://media.apotea.se/product-images/MS/bluetouch-rem-f%c3%b6r-nedre-delen-av-ryggen-tillbeh%c3%b6r-0.jpg\" alt=\"BlueTouch rem f&#246;r nedre delen av ryggen (tillbeh&#246;r)\" />                    \n" +
                    "                </a>                \n" +
                    "            </div>                \n" +
                    "            <div style=\"position: relative;\">                    \n" +
                    "                <div class=\"price-tag\" onclick=\"document.location='/bluetouch-rem-f&#246;r-nedre-delen-av-ryggen-tillbeh&#246;r'\">                            \n" +
                    "                    <a href=\"/bluetouch-rem-f&#246;r-nedre-delen-av-ryggen-tillbeh&#246;r\"><span class=\"\">279 kr</span></a>                    \n" +
                    "                </div>                   \n" +
                    "                <div class=\"badges\">                            \n" +
                    "                    <div class=\"promotion-badge\">\n" +
                    "                        <a href=\"/bluetouch-rem-f&#246;r-nedre-delen-av-ryggen-tillbeh&#246;r\">\n" +
                    "                            <img src=\"/content/images/6/kampanj_kampanj.svg\" onerror=\"this.onerror = null; this.src = '/content/images/6/kampanj_kampanj.png'\" />\n" +
                    "                        </a>\n" +
                    "                    </div>                                           \n" +
                    "                </div>                                    \n" +
                    "            </div>                \n" +
                    "            <div id=\"list-116092\">                    <div class=\"is-med-div\"></div>                    <div class=\"name\" style=\"overflow:hidden;height:32px;\"><a href=\"/bluetouch-rem-f&#246;r-nedre-delen-av-ryggen-tillbeh&#246;r\">BlueTouch rem f&#246;r nedre delen av ryggen (tillbeh&#246;r)</a></div>                    <div class=\"list-box\">                                <div class=\"store-price\">Vårt ord.pris: 379 kr</div>                            <img class=\"list-add\" data-sku=\"116092\" src=\"/content/images/6/add-to-cart.png\" alt=\"Lägg 1 st BlueTouch rem f&#246;r nedre delen av ryggen (tillbeh&#246;r) i varukorgen\" />                        <div style=\"clear:both;\"></div>                    </div>                </div>            </li>            <li class=\"\">                <div class=\"img center\">                    <a href=\"/bluetouch-rem-f&#246;r-&#246;vre-delen-av-ryggen-tillbeh&#246;r\">                        <img src=\"http://media.apotea.se/product-images/MS/bluetouch-rem-f%c3%b6r-%c3%b6vre-delen-av-ryggen-tillbeh%c3%b6r-0.jpg\" alt=\"BlueTouch rem f&#246;r &#246;vre delen av ryggen (tillbeh&#246;r)\" />                    </a>                </div>                <div style=\"position: relative;\">                    <div class=\"price-tag\" onclick=\"document.location='/bluetouch-rem-f&#246;r-&#246;vre-delen-av-ryggen-tillbeh&#246;r'\">                            <a href=\"/bluetouch-rem-f&#246;r-&#246;vre-delen-av-ryggen-tillbeh&#246;r\"><span class=\"\">299 kr</span></a>                    </div>                    <div class=\"badges\">                            <div class=\"promotion-badge\"><a href=\"/bluetouch-rem-f&#246;r-&#246;vre-delen-av-ryggen-tillbeh&#246;r\"><img src=\"/content/images/6/kampanj_kampanj.svg\" onerror=\"this.onerror = null; this.src = '/content/images/6/kampanj_kampanj.png'\" /></a></div>                                            </div>                                    </div>                <div id=\"list-116091\">                    <div class=\"is-med-div\"></div>                    <div class=\"name\" style=\"overflow:hidden;height:32px;\"><a href=\"/bluetouch-rem-f&#246;r-&#246;vre-delen-av-ryggen-tillbeh&#246;r\">BlueTouch rem f&#246;r &#246;vre delen av ryggen (tillbeh&#246;r)</a></div>                    <div class=\"list-box\">                                <div class=\"store-price\">Vårt ord.pris: 399 kr</div>                            <img class=\"list-add\" data-sku=\"116091\" src=\"/content/images/6/add-to-cart.png\" alt=\"Lägg 1 st BlueTouch rem f&#246;r &#246;vre delen av ryggen (tillbeh&#246;r) i varukorgen\" />                        <div style=\"clear:both;\"></div>                    </div>                </div>            </li>\n" +
                    "\n" +
                    "    </ul>\n" +
                    "        </div>\n" +
                    "</body>";

    private static final String kronanSearchRes =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"sv\">\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<li class=\"productListItem productListItemPromotion\">\n"+
            "\t\t\t<a href=\"/ACO-Sun-Active-Body-Spray-SPF-20/p/768996\" title=\"Sun Active Body Spray SPF 20\" class=\"productMainLink\" data-code=\"768996\">\n"+
            "\t\t\t\t\t<figure class=\"thumb\">\n"+
            "\t\t\t\t\t\t<img src=\"/medias/sys_master/images/images/h7e/h18/8813506592798.jpg\" alt=\"Sun Active Body Spray SPF 20\"\n"+
            "\t\t\t\t\t title=\"Sun Active Body Spray SPF 20\"/>\n"+
            "\t\t\t<figcaption class=\"splash standard\">Köp 2 - Få 20% </figcaption>\n"+
            "\t\t<!-- Only one ribbon wanted -->\n"+
            "\t\t</figure>\n"+
            "\t\t\t\t\t<h3 class=\"productBrand\" title=\"ACO\">ACO</h3>\n"+
            "                        <h4 class=\"productName\" title=\"Sun Active Body Spray SPF 20\">Sun Active Body Spray SPF 20</h4>\n"+
            "                        <p class=\"packSize\">125 ml</p>\n"+
            "\t\t\t\t\t<p class=\"price\">\n"+
            "\t\t\t\t\t\t\t149:-</p>\n"+
            "\t\t\t\t\t</a>\n"+
            "\n"+
            "\t\t\t\t<div id=\"actions-container-for-SearchResultsGrid\" class=\"listAddPickupContainer\">\n"+
            "\t\t\t\t\t<div id=\"SearchResultsGrid-ListPickUpInStoreAction\" data-index=\"1\" class=\"\">\n"+
            "\t\t\t</div>\n"+
            "\t<div id=\"SearchResultsGrid-ListAddToCartAction\" data-index=\"2\" class=\"\">\n"+
            "\t\t\t<div class=\"cart clearfix\">\n"+
            "\t<form id=\"addToCartForm768996\" class=\"add_to_cart_form\" action=\"/cart/add\" method=\"post\"><input type=\"hidden\" name=\"productCodePost\" value=\"768996\"/>\n"+
            "\t\t\t\t\t<button id=\"addToCartButton\" type=\"submit\" class=\"addToCartButton\" disabled=\"disabled\">\n"+
            "\t\t\t\t\t\t\t\tK&ouml;p</button>\n"+
            "\t\t\t\t\t\t<input type=\"hidden\" name=\"CSRFToken\" value=\"0875a483-250b-437c-8e28-149d9b08c335\" />\n"+
            "</form></div>\n"+
            "</div>\n"+
            "\t</div>\n"+
            "\n"+
            "\t\t\t</li>\n"+
            "\t<li class=\"productListItem separator\">\n"+
            "\t\t\t<img src=\"/_ui/desktop/common/images/recResultSeparator.png\" alt=\"Här börjar listan med rekommenderade produkter\"/>\n"+
            "\t\t</li>\n"+
            "\t<li class=\"productListItem productListItemPromotion\">\n"+
            "\t\t\t<a href=\"/ACO-Sun-Kids-Pump-Spray-SPF-30/p/768991\" title=\"Sun Kids Pump Spray SPF 30\" class=\"productMainLink\" data-code=\"768991\">\n"+
            "\t\t\t\t\t<figure class=\"thumb\">\n"+
            "\t\t\t\t\t\t<img src=\"/medias/sys_master/images/images/h59/hbe/8813506265118.jpg\" alt=\"Sun Kids Pump Spray SPF 30\"\n"+
            "\t\t\t\t\t title=\"Sun Kids Pump Spray SPF 30\"/>\n"+
            "\t\t\t<figcaption class=\"splash standard\">Köp 2 - Få 20% </figcaption>\n"+
            "\t\t<!-- Only one ribbon wanted -->\n"+
            "\t\t</figure>\n"+
            "\t\t\t\t\t<h3 class=\"productBrand\" title=\"ACO\">ACO</h3>\n"+
            "                        <h4 class=\"productName\" title=\"Sun Kids Pump Spray SPF 30\">Sun Kids Pump Spray SPF 30</h4>\n"+
            "                        <p class=\"packSize\">175 ml</p>\n"+
            "\t\t\t\t\t<p class=\"price\">\n"+
            "\t\t\t\t\t\t\t159:-</p>\n"+
            "\t\t\t\t\t</a>\n"+
            "\n"+
            "\t\t\t\t<div id=\"actions-container-for-SearchResultsGrid\" class=\"listAddPickupContainer\">\n"+
            "\t\t\t\t\t<div id=\"SearchResultsGrid-ListPickUpInStoreAction\" data-index=\"1\" class=\"\">\n"+
            "\t\t\t</div>\n"+
            "\t<div id=\"SearchResultsGrid-ListAddToCartAction\" data-index=\"2\" class=\"\">\n"+
            "\t\t\t<div class=\"cart clearfix\">\n"+
            "\t<form id=\"addToCartForm768991\" class=\"add_to_cart_form\" action=\"/cart/add\" method=\"post\"><input type=\"hidden\" name=\"productCodePost\" value=\"768991\"/>\n"+
            "\t\t\t\t\t<button id=\"addToCartButton\" type=\"submit\" class=\"addToCartButton\" disabled=\"disabled\">\n"+
            "\t\t\t\t\t\t\t\tK&ouml;p</button>\n"+
            "\t\t\t\t\t\t<input type=\"hidden\" name=\"CSRFToken\" value=\"0875a483-250b-437c-8e28-149d9b08c335\" />\n"+
            "</form></div>\n"+
            "</div>\n"+
            "\t</div>\n"+
            "\n"+
            "\t\t\t</li>";

    private static final String html = "<div id=\"d1\">" + //
            "<div id=\"d1.0\">" + //
            "<div id=\"d1.0.0\">" + //
            "1.0.0" + //
            "</div>" + //
            "<div id=\"d1.0.1\">" + //
            "1.0.1" + //
            "</div>" + //
            "</div>" + //
            "<div id=\"d1.1\">" + //
            "1.1" + //
            "</div>" + //
            "<div id=\"d1.3\">" + //
            "1.3" + //
            "</div>" + //
            "</div>";


    public static void main(String[] args) throws IOException {

        List<String> childList = new ArrayList<String>();
        NodeVisitor myNodeVisitor = new MyNodeVisitor(childList);

        ParserInterface parser = new KronanSearchParser();
        ParseResult result = new ParseResult();
        parser.execute("no url", kronanSearchRes, "", result);

        System.out.println("Got result: " + result.toString());


    }


}



class MyNodeVisitor implements NodeVisitor {

            private List<String> childList;

            public MyNodeVisitor(List<String> childList) {
                if (childList == null) {
                    throw new NullPointerException("child cannot be null.");
                }

                this.childList = childList;
            }

            @Override
            public void head(Node node, int depth) {
                if (node.childNodeSize() == 0) {
                    childList.add(node.toString());
                }
            }

            @Override
            public void tail(Node node, int depth) {

            }

}



