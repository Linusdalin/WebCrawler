package test;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import medicinpriser.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class MedicinPricerTest {

    private static final Category[] testCategories = { new Category(1, "Allergi"), new Category(2, "Barn"), };
    private static final String productResponse = "[{\"id\":332570,\"ArticleId\":214,\"productNumber\":\"439962\",\"price\":43,\"retailPrice\":43,\"lastChecked\":\"2017-04-14T12:07:56.563Z\",\"retailLastChecked\":\"2017-04-14T12:07:56.565Z\",\"ShopItem\":{\"id\":4716,\"url\":\"https://www.kronansapotek.se/Desloratadine-Sandoz-Filmdragerad-tablett/p/439962\",\"data\":{\"stockLevel\":2},\"Shop\":{\"id\":4,\"name\":\"Kronans Apotek\",\"url\":\"https://www.kronansapotek.se\",\"slug\":\"kronans-apotek\",\"data\":{\"store\":true,\"registered\":true}}}},{\"id\":320681,\"ArticleId\":214,\"productNumber\":\"439962\",\"price\":43,\"retailPrice\":59.9,\"lastChecked\":\"2017-04-14T05:11:50.619Z\",\"retailLastChecked\":\"2017-04-14T05:11:50.620Z\",\"ShopItem\":{\"id\":12352,\"url\":\"https://www.apotekhjartat.se/symtom/allergi/desloratadine-sandoz-filmdragerad-tablett-86519af5/\",\"data\":{\"stockLevel\":2},\"Shop\":{\"id\":1,\"name\":\"Apotek Hjärtat\",\"url\":\"https://www.apotekhjartat.se\",\"slug\":\"apotek-hjartat\",\"data\":{\"store\":true,\"registered\":true}}}},{\"id\":331719,\"ArticleId\":214,\"productNumber\":\"439962\",\"price\":59.5,\"retailPrice\":59.5,\"lastChecked\":\"2017-04-14T03:04:42.516Z\",\"retailLastChecked\":\"2017-04-14T03:04:42.517Z\",\"ShopItem\":{\"id\":13012,\"url\":\"https://www.apoteket.se/produkt/desloratadine-sandoz-filmdragerad-tablett-5-mg-30-tabletter-blister-254470/\",\"data\":{\"stockLevel\":2},\"Shop\":{\"id\":2,\"name\":\"Apoteket\",\"url\":\"https://www.apoteket.se/\",\"slug\":\"apoteket\",\"data\":{\"store\":true,\"registered\":true}}}},{\"id\":332197,\"ArticleId\":214,\"productNumber\":\"439962\",\"price\":79.2,\"lastChecked\":\"2017-04-14T09:15:48.509Z\",\"ShopItem\":{\"id\":9758,\"url\":\"https://www.apotea.se/desloratadine-sandoz-filmdragerad-tablett-5-mg-30-st\",\"data\":{\"stockLevel\":2},\"Shop\":{\"id\":3,\"name\":\"Apotea\",\"url\":\"https://www.apotea.se/\",\"slug\":\"apotea\",\"data\":{\"store\":false,\"shopItemDefaultUrl\":\"https://www.apotea.se/bestall-receptbelagd-medicin\",\"registered\":true}}}}]";
    private static final String categoryResponse = "[{\"id\":1571,\"slug\":\"loratadin-actavis-tablett-10-mg\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Loratadin Actavis\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"28 styck\",\"numeric\":28,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"017147\"},\"availablePackagesLength\":4,\"price\":{\"max\":44.5,\"min\":14,\"savePercentage\":69,\"diff\":30.5,\"diffPercentage\":0.685393258426966},\"image\":\"3/36440/e91520d49278581b8d58937ad0013c2f.jpg\"},{\"id\":4414,\"slug\":\"vialerg-filmdragerad-tablett-10-mg\",\"substances\":[\"Cetirizin\"],\"data\":{\"tradeName\":\"Vialerg\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"593595\"},\"availablePackagesLength\":3,\"price\":{\"max\":49.9,\"min\":17,\"savePercentage\":66,\"diff\":32.9,\"diffPercentage\":0.659318637274549},\"image\":\"1/37852/fca5571bf213f8a6655c39f22f4f22f2.jpg\"},{\"id\":5908,\"slug\":\"cetirizine-teva-filmdragerad-tablett-10-mg\",\"substances\":[\"Cetirizin\"],\"data\":{\"tradeName\":\"Cetirizine Teva\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"14 tablett(er)\",\"numeric\":14,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"421229\"},\"availablePackagesLength\":3,\"price\":{\"max\":44.5,\"min\":16,\"savePercentage\":64,\"diff\":28.5,\"diffPercentage\":0.640449438202247},\"image\":\"3/9928/11314b898013ee7faa3d76236185c591.jpg\"},{\"id\":1586,\"slug\":\"loratadin-ratiopharm-tablett-10-mg\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Loratadin ratiopharm\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"10 styck\",\"numeric\":10,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"016798\"},\"availablePackagesLength\":5,\"price\":{\"max\":22.5,\"min\":9,\"savePercentage\":60,\"diff\":13.5,\"diffPercentage\":0.6},\"image\":\"1/17917/edd2066e11a8379797054e99ceef5b6d.jpg\"},{\"id\":5912,\"slug\":\"cetirizin-sandoz-filmdragerad-tablett-10-mg\",\"substances\":[\"Cetirizin\"],\"data\":{\"tradeName\":\"Cetirizin Sandoz\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 styck\",\"numeric\":30,\"numericUnit\":\"st\"},\"price\":78.73,\"available\":true,\"prescription\":false,\"productNumber\":\"170207\"},\"availablePackagesLength\":3,\"price\":{\"max\":78.73,\"min\":39,\"savePercentage\":50,\"diff\":39.73,\"diffPercentage\":0.504636098056649,\"fixed\":78.73},\"image\":\"3/37134/55ed36e8972e695acc64a07d425a12ad.jpg\"},{\"id\":214,\"slug\":\"desloratadine-sandoz-filmdragerad-tablett-5-mg\",\"substances\":[\"Desloratadin\"],\"data\":{\"tradeName\":\"Desloratadine Sandoz\",\"strength\":{\"text\":\"5 mg\",\"numeric\":5,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"price\":79.2,\"available\":true,\"prescription\":false,\"productNumber\":\"439962\"},\"availablePackagesLength\":3,\"price\":{\"max\":79.2,\"min\":43,\"savePercentage\":46,\"diff\":36.2,\"diffPercentage\":0.457070707070707,\"fixed\":79.2},\"image\":\"1/12352/210c9fc223830e9bc96a71c92ef78543.jpg\"},{\"id\":2200,\"slug\":\"mommox-nasspray-suspension-50-mikrog-dos\",\"substances\":[\"Mometasonfuroat\"],\"data\":{\"tradeName\":\"Mommox\",\"strength\":{\"text\":\"50 mikrog/dos\",\"numeric\":50,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"60 dos(er)\",\"numeric\":60,\"numericUnit\":\"st\"},\"price\":93.12,\"available\":true,\"prescription\":false,\"productNumber\":\"376754\"},\"availablePackagesLength\":3,\"price\":{\"max\":116,\"min\":79,\"savePercentage\":32,\"diff\":37,\"diffPercentage\":0.318965517241379,\"fixed\":93.12},\"image\":\"1/9576/86bc6e680e69c80d4226ac095ca53f59.jpg\"},{\"id\":1424,\"slug\":\"livostin-nasspray-suspension-50-mikrog-dos\",\"substances\":[\"Levokabastin\"],\"data\":{\"tradeName\":\"Livostin\",\"strength\":{\"text\":\"50 mikrog/dos\",\"numeric\":50,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"150 dos(er)\",\"numeric\":150,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"567545\"},\"availablePackagesLength\":2,\"price\":{\"max\":154,\"min\":108,\"savePercentage\":30,\"diff\":46,\"diffPercentage\":0.298701298701299},\"image\":\"1/2660/b856586496a5cc3d532f0eabb20a83ee.jpg\"},{\"id\":5845,\"slug\":\"flutikason-teva-nasspray-suspension-50-mikrog-dos\",\"substances\":[\"Flutikasonpropionat\"],\"data\":{\"tradeName\":\"Flutikason Teva\",\"strength\":{\"text\":\"50 mikrog/dos\",\"numeric\":50,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"60 dos(er)\",\"numeric\":60,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"076214\"},\"availablePackagesLength\":3,\"price\":{\"max\":89.5,\"min\":63,\"savePercentage\":30,\"diff\":26.5,\"diffPercentage\":0.29608938547486},\"image\":\"1/15447/b3727428e7516cf8cf53025e0efa9a5c.jpg\"},{\"id\":272,\"slug\":\"hydrokortison-ccs-kram-1\",\"substances\":[\"Hydrokortison\"],\"data\":{\"tradeName\":\"Hydrokortison CCS\",\"strength\":{\"text\":\"10 mg/g\",\"numeric\":10,\"numericUnit\":\"mg/g\"},\"registrationForm\":\"Kräm\"},\"selectedPackage\":{\"desc\":\"Tub\",\"size\":{\"text\":\"50 gram\",\"numeric\":50,\"numericUnit\":\"g\"},\"available\":true,\"prescription\":false,\"productNumber\":\"429787\"},\"availablePackagesLength\":3,\"price\":{\"max\":79,\"min\":56,\"savePercentage\":29,\"diff\":23,\"diffPercentage\":0.291139240506329},\"image\":\"1/29489/8f5a79b9a7f57e25748d41c5142a5e82.jpg\"},{\"id\":275,\"slug\":\"hydrokortison-ccs-salva-1\",\"substances\":[\"Hydrokortison\"],\"data\":{\"tradeName\":\"Hydrokortison CCS\",\"strength\":{\"text\":\"10 mg/g\",\"numeric\":10,\"numericUnit\":\"mg/g\"},\"registrationForm\":\"Salva\"},\"selectedPackage\":{\"desc\":\"Tub\",\"size\":{\"text\":\"50 gram\",\"numeric\":50,\"numericUnit\":\"g\"},\"available\":true,\"prescription\":false,\"productNumber\":\"429894\"},\"availablePackagesLength\":3,\"price\":{\"max\":79,\"min\":56,\"savePercentage\":29,\"diff\":23,\"diffPercentage\":0.291139240506329},\"image\":\"1/15883/82e2c0315a99df09d78617f9b0f76477.jpg\"},{\"id\":622,\"slug\":\"allegra-filmdragerad-tablett-120-mg\",\"substances\":[\"Fexofenadin\"],\"data\":{\"tradeName\":\"Allegra\",\"strength\":{\"text\":\"120 mg\",\"numeric\":120,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"533156\"},\"availablePackagesLength\":3,\"price\":{\"max\":167,\"min\":121,\"savePercentage\":28,\"diff\":46,\"diffPercentage\":0.275449101796407},\"image\":\"1/3673/dae74dd6f8bfc0d0f569dce832f45202.jpg\"},{\"id\":256,\"slug\":\"desonix-nasspray-suspension-32-mikrog-dos\",\"substances\":[\"Budesonid\"],\"data\":{\"tradeName\":\"Desonix\",\"strength\":{\"text\":\"32 mikrog/dos\",\"numeric\":32,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Spraybehållare\",\"size\":{\"text\":\"120 dos(er)\",\"numeric\":120,\"numericUnit\":\"st\"},\"price\":90.84,\"available\":true,\"prescription\":false,\"productNumber\":\"029687\"},\"availablePackagesLength\":2,\"price\":{\"max\":90.84,\"min\":66,\"savePercentage\":27,\"diff\":24.84,\"diffPercentage\":0.273447820343461,\"fixed\":90.84},\"image\":\"1/14951/44b956d4943d973372de2d6e741f525c.jpg\"},{\"id\":1582,\"slug\":\"loratadin-orifarm-tablett-10-mg\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Loratadin Orifarm\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"164331\"},\"availablePackagesLength\":4,\"price\":{\"max\":24,\"min\":18,\"savePercentage\":25,\"diff\":6,\"diffPercentage\":0.25},\"image\":\"1/4959/2324f7b87d708a848cd0236ce39529b1.jpg\"},{\"id\":4829,\"slug\":\"ebastin-orifarm-filmdragerad-tablett-10-mg\",\"substances\":[\"Ebastin\"],\"data\":{\"tradeName\":\"Ebastin Orifarm\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 styck\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"061426\"},\"availablePackagesLength\":3,\"price\":{\"max\":129,\"min\":98,\"savePercentage\":24,\"diff\":31,\"diffPercentage\":0.24031007751938},\"image\":\"1/3740/c11c0d4cc30c8afeb80b08bdab3b9ab2.jpg\"},{\"id\":726,\"slug\":\"altifex-filmdragerad-tablett-120-mg\",\"substances\":[\"Fexofenadin\"],\"data\":{\"tradeName\":\"Altifex\",\"strength\":{\"text\":\"120 mg\",\"numeric\":120,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 styck\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"414063\"},\"availablePackagesLength\":2,\"price\":{\"max\":129,\"min\":99,\"savePercentage\":23,\"diff\":30,\"diffPercentage\":0.232558139534884},\"image\":\"3/6372/b756e3a8655da3c3460ea974c0a44ca8.jpg\"},{\"id\":6263,\"slug\":\"clarityn-tablett-10-mg\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Clarityn\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"28 styck\",\"numeric\":28,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"005571\"},\"availablePackagesLength\":2,\"price\":{\"max\":159,\"min\":123,\"savePercentage\":23,\"diff\":36,\"diffPercentage\":0.226415094339623},\"image\":\"1/6864/518a79e9867e78c490a00980caf8b2c1.jpg\"},{\"id\":4616,\"slug\":\"zaditen-ogondroppar-losning-0-25-mg-ml\",\"substances\":[\"Ketotifen\"],\"data\":{\"tradeName\":\"Zaditen\",\"strength\":{\"text\":\"0,25 mg/ml\",\"numeric\":0.25,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"5 milliliter\",\"numeric\":5,\"numericUnit\":\"ml\"},\"price\":133.77,\"available\":true,\"prescription\":false,\"productNumber\":\"076403\"},\"availablePackagesLength\":1,\"price\":{\"max\":169,\"min\":133.77,\"savePercentage\":21,\"diff\":35.23,\"diffPercentage\":0.208461538461538,\"fixed\":133.77},\"image\":\"1/4550/61a18eb633caeca5443a27aa675bd651.jpg\"},{\"id\":3462,\"slug\":\"rhinocort-aqua-nasspray-suspension-32-mikrog-dos\",\"substances\":[\"Budesonid\"],\"data\":{\"tradeName\":\"Rhinocort Aqua\",\"strength\":{\"text\":\"32 mikrog/dos\",\"numeric\":32,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"120 dos(er)\",\"numeric\":120,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"448126\"},\"availablePackagesLength\":2,\"price\":{\"max\":99.9,\"min\":79.6,\"savePercentage\":20,\"diff\":20.3,\"diffPercentage\":0.203203203203203},\"image\":\"1/8276/e2983a3a8a5370b51e9340111dadd526.jpg\"},{\"id\":6260,\"slug\":\"clarityn-sirap-1-mg-ml\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Clarityn\",\"strength\":{\"text\":\"1 mg/ml\",\"numeric\":1,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Sirap\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"100 milliliter\",\"numeric\":100,\"numericUnit\":\"ml\"},\"available\":true,\"prescription\":false,\"productNumber\":\"016431\"},\"availablePackagesLength\":1,\"price\":{\"max\":119,\"min\":95,\"savePercentage\":20,\"diff\":24,\"diffPercentage\":0.201680672268908},\"image\":\"1/420/abd2de04f6a65166e249c8359e544b3f.jpg\"},{\"id\":492,\"slug\":\"kestine-filmdragerad-tablett-10-mg\",\"substances\":[\"Ebastin\"],\"data\":{\"tradeName\":\"Kestine\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"013898\"},\"availablePackagesLength\":3,\"price\":{\"max\":149,\"min\":119,\"savePercentage\":20,\"diff\":30,\"diffPercentage\":0.201342281879195},\"image\":\"1/30272/8050eed8009f4d2ffc407cc5001f5495.jpg\"},{\"id\":4617,\"slug\":\"zaditen-ogondroppar-losning-i-endosbehallare-0-25-mg-ml\",\"substances\":[\"Ketotifen\"],\"data\":{\"tradeName\":\"Zaditen\",\"strength\":{\"text\":\"0,25 mg/ml\",\"numeric\":0.25,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning i endosbehållare\"},\"selectedPackage\":{\"desc\":\"Endosbehållare\",\"size\":{\"text\":\"60 x 0,4 milliliter\",\"numeric\":24,\"numericUnit\":\"ml\"},\"price\":354.78,\"available\":true,\"prescription\":false,\"productNumber\":\"150162\"},\"availablePackagesLength\":2,\"price\":{\"max\":444,\"min\":354.78,\"savePercentage\":20,\"diff\":89.22,\"diffPercentage\":0.200945945945946,\"fixed\":354.78},\"image\":\"1/1724/ebad3e6dfdcfcfce3a57ca9160ab5fbc.jpg\"},{\"id\":1533,\"slug\":\"lomudal-ogondroppar-losning-i-endosbehallare-40-mg-ml\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Lomudal\",\"strength\":{\"text\":\"40 mg/ml\",\"numeric\":40,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning i endosbehållare\"},\"selectedPackage\":{\"desc\":\"Endosbehållare\",\"size\":{\"text\":\"3 x 20 dos(er)\",\"numeric\":60,\"numericUnit\":\"st\"},\"price\":176.91,\"available\":true,\"prescription\":false,\"productNumber\":\"018434\"},\"availablePackagesLength\":2,\"price\":{\"max\":189,\"min\":151.2,\"savePercentage\":20,\"diff\":37.8,\"diffPercentage\":0.2,\"fixed\":176.91},\"image\":\"1/29772/6d143f617be097f22077cba5ddc48883.jpg\"},{\"id\":2188,\"slug\":\"mometason-apofri-nasspray-suspension-50-mikrog-dos\",\"substances\":[\"Mometasonfuroat\"],\"data\":{\"tradeName\":\"Mometason Apofri\",\"strength\":{\"text\":\"50 mikrog/dos\",\"numeric\":50,\"numericUnit\":\"mikrog/dos\"},\"registrationForm\":\"Nässpray, suspension\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"140 dos(er)\",\"numeric\":140,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"390439\"},\"availablePackagesLength\":2,\"price\":{\"max\":199,\"min\":159.2,\"savePercentage\":20,\"diff\":39.8,\"diffPercentage\":0.2},\"image\":\"2/984/9671cd71db3ff047876c0a3bdf340896.jpg\"},{\"id\":1529,\"slug\":\"lomudal-ogondroppar-losning-20-mg-ml\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Lomudal\",\"strength\":{\"text\":\"20 mg/ml\",\"numeric\":20,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"5 milliliter\",\"numeric\":5,\"numericUnit\":\"ml\"},\"available\":true,\"prescription\":false,\"productNumber\":\"123652\"},\"availablePackagesLength\":2,\"price\":{\"max\":55,\"min\":44,\"savePercentage\":20,\"diff\":11,\"diffPercentage\":0.2},\"image\":\"1/11287/8df225f6abefd3e14c3ccc792c41176e.jpg\"},{\"id\":5904,\"slug\":\"cetirizin-apofri-filmdragerad-tablett-10-mg\",\"substances\":[\"Cetirizin\"],\"data\":{\"tradeName\":\"Cetirizin Apofri\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"050557\"},\"availablePackagesLength\":2,\"price\":{\"max\":49.5,\"min\":39.6,\"savePercentage\":20,\"diff\":9.9,\"diffPercentage\":0.2},\"image\":\"2/34203/f31305f29a26eba232f8a56cdde80b9b.jpg\"},{\"id\":628,\"slug\":\"allgil-ogondroppar-losning-20-mg-ml\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Allgil\",\"strength\":{\"text\":\"20 mg/ml\",\"numeric\":20,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"10 milliliter\",\"numeric\":10,\"numericUnit\":\"ml\"},\"available\":true,\"prescription\":false,\"productNumber\":\"546039\"},\"availablePackagesLength\":1,\"price\":{\"max\":69,\"min\":55.2,\"savePercentage\":20,\"diff\":13.8,\"diffPercentage\":0.2},\"image\":\"2/30751/6cea2ab0d100e2f52c2ab6bf670fbd3d.jpg\"},{\"id\":1575,\"slug\":\"loratadin-apofri-tablett-10-mg\",\"substances\":[\"Loratadin\"],\"data\":{\"tradeName\":\"Loratadin Apofri\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"30 tablett(er)\",\"numeric\":30,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"495405\"},\"availablePackagesLength\":2,\"price\":{\"max\":41.5,\"min\":33.2,\"savePercentage\":20,\"diff\":8.3,\"diffPercentage\":0.2},\"image\":\"2/1192/b9a694422d621b8ebcfa1878fe1cd0fe.jpg\"},{\"id\":960,\"slug\":\"lecrolyn-ogondroppar-losning-i-endosbehallare-40-mg-ml\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Lecrolyn\",\"strength\":{\"text\":\"40 mg/ml\",\"numeric\":40,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning i endosbehållare\"},\"selectedPackage\":{\"desc\":\"Endosbehållare\",\"size\":{\"text\":\"60 x 1 dos(er)\",\"numeric\":60,\"numericUnit\":\"st\"},\"price\":176.91,\"available\":true,\"prescription\":false,\"productNumber\":\"012869\"},\"availablePackagesLength\":2,\"price\":{\"max\":219,\"min\":176.91,\"savePercentage\":19,\"diff\":42.09,\"diffPercentage\":0.192191780821918,\"fixed\":176.91},\"image\":\"1/15442/b578ed5e9a5c4f4421c2a4d8bbe7481e.jpg\"},{\"id\":498,\"slug\":\"kestine-frystorkad-tablett-10-mg\",\"substances\":[\"Ebastin\"],\"data\":{\"tradeName\":\"Kestine\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Frystorkad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"10 tablett(er)\",\"numeric\":10,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"027783\"},\"availablePackagesLength\":1,\"price\":{\"max\":79.5,\"min\":65,\"savePercentage\":18,\"diff\":14.5,\"diffPercentage\":0.182389937106918},\"image\":\"1/2880/b68be10ba1bd8005d7a53b03b8333f5a.jpg\"},{\"id\":4824,\"slug\":\"zyrlex-filmdragerad-tablett-10-mg\",\"substances\":[\"Cetirizin\"],\"data\":{\"tradeName\":\"Zyrlex\",\"strength\":{\"text\":\"10 mg\",\"numeric\":10,\"numericUnit\":\"mg\"},\"registrationForm\":\"Filmdragerad tablett\"},\"selectedPackage\":{\"desc\":\"Blister\",\"size\":{\"text\":\"14 styck\",\"numeric\":14,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"000954\"},\"availablePackagesLength\":4,\"price\":{\"max\":99.5,\"min\":85,\"savePercentage\":15,\"diff\":14.5,\"diffPercentage\":0.14572864321608},\"image\":\"1/37875/0e3fa484b31e22a261a3169a53c6f490.jpg\"},{\"id\":1543,\"slug\":\"lomudal-nasal-nasspray-losning-5-2-mg-dos\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Lomudal Nasal\",\"strength\":{\"text\":\"5,2 mg/dos\",\"numeric\":5.2,\"numericUnit\":\"mg/dos\"},\"registrationForm\":\"Nässpray, lösning\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"100 dos(er)\",\"numeric\":100,\"numericUnit\":\"st\"},\"available\":true,\"prescription\":false,\"productNumber\":\"037720\"},\"availablePackagesLength\":2,\"price\":{\"max\":139,\"min\":119,\"savePercentage\":14,\"diff\":20,\"diffPercentage\":0.143884892086331},\"image\":\"1/35186/dc6262e5e0c7ead098ea101147be89f5.jpg\"},{\"id\":958,\"slug\":\"lecrolyn-ogondroppar-losning-i-endosbehallare-20-mg-ml\",\"substances\":[\"Kromoglicinsyra\"],\"data\":{\"tradeName\":\"Lecrolyn\",\"strength\":{\"text\":\"20 mg/ml\",\"numeric\":20,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning i endosbehållare\"},\"selectedPackage\":{\"desc\":\"Endosbehållare\",\"size\":{\"text\":\"60 styck\",\"numeric\":60,\"numericUnit\":\"st\"},\"price\":130.23,\"available\":true,\"prescription\":false,\"productNumber\":\"013538\"},\"availablePackagesLength\":1,\"price\":{\"max\":169,\"min\":145,\"savePercentage\":14,\"diff\":24,\"diffPercentage\":0.142011834319527,\"fixed\":130.23},\"image\":\"1/16595/0bc873552d9521282949452c8f52bb7d.jpg\"},{\"id\":2049,\"slug\":\"mildison-lipid-kram-1\",\"substances\":[\"Hydrokortison\"],\"data\":{\"tradeName\":\"Mildison Lipid\",\"strength\":{\"text\":\"1 %\",\"numeric\":1,\"numericUnit\":\"%\"},\"registrationForm\":\"Kräm\"},\"selectedPackage\":{\"desc\":\"Tub\",\"size\":{\"text\":\"30 gram\",\"numeric\":30,\"numericUnit\":\"g\"},\"price\":55.89,\"available\":true,\"prescription\":false,\"productNumber\":\"008194\"},\"availablePackagesLength\":3,\"price\":{\"max\":55.89,\"min\":49,\"savePercentage\":12,\"diff\":6.89,\"diffPercentage\":0.12327786723922,\"fixed\":55.89},\"image\":\"1/19090/def14ba7f4e9dfba065abd025ea3ca5c.jpg\"},{\"id\":4044,\"slug\":\"tilavist-ogondroppar-losning-20-mg-ml\",\"substances\":[\"Nedokromil\"],\"data\":{\"tradeName\":\"Tilavist\",\"strength\":{\"text\":\"20 mg/ml\",\"numeric\":20,\"numericUnit\":\"mg/ml\"},\"registrationForm\":\"Ögondroppar, lösning\"},\"selectedPackage\":{\"desc\":\"Flaska\",\"size\":{\"text\":\"5 milliliter\",\"numeric\":5,\"numericUnit\":\"ml\"},\"price\":84.33,\"available\":true,\"prescription\":false,\"productNumber\":\"020249\"},\"availablePackagesLength\":1,\"price\":{\"max\":99,\"min\":87,\"savePercentage\":12,\"diff\":12,\"diffPercentage\":0.121212121212121,\"fixed\":84.33},\"image\":\"1/16900/614b861dba0d7018a2256cd5d36e14b5.jpg\"}]";


    @Test
    public void testExtractProductRequests(){

        try {

            JSONArray products = new JSONArray(categoryResponse);
            System.out.println("Got: \n" + products.toString());

            CategoryFetcher fetcher = new CategoryFetcher();
            List<Product> productList = fetcher.getProductRequests(new Category(0, "test"), products);

            System.out.println(" Got: " + productList.size() + " products");
            System.out.println(" Got: " + productList.toString() );


        } catch (JSONException e) {

            e.printStackTrace();
            assertTrue(false);
        }

    }


    @Test
    public void writeFromJson(){

        try {

            JSONArray prices = new JSONArray(productResponse);
            TableWriter tableWriter = new TableWriter();
            tableWriter.addProductLine(new Product("testProduct", "size", 0, "0", new Category(0, "test")), prices);
            System.out.println("Result: \n" + tableWriter.toString());

        } catch (JSONException e) {

            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void excelFromJson(){

        try {

            JSONArray prices = new JSONArray(productResponse);
            TableWriter tableWriter = new TableWriter();
            tableWriter.addProductLine(new Product("testProduct", "size", 0, "0", new Category(0, "test")), prices);
            WritableWorkbook excellDoc = tableWriter.toExcel("medicinpriser.xls");
            excellDoc.write();
            excellDoc.close();

        } catch (JSONException | WriteException e) {

            e.printStackTrace();
            assertTrue(false);
        } catch (IOException e) {

            e.printStackTrace();
            assertTrue(false);
        }

    }



    @Test
    public void productRequestTest(){

        ProductFetcher fetcher = new ProductFetcher();
        JSONArray result = fetcher.getProductJSON(new Product("Name", "Size", 214, "439962", new Category(0, "test")));

        System.out.println("Got:\n" + result);

    }

    @Test
    public void categoryRequestTest(){

        CategoryFetcher fetcher = new CategoryFetcher();
        JSONArray result = fetcher.getCategoryJSON(1);

        System.out.println("Got:\n" + result);

    }


    @Test
    public void acceptanceTest() throws JSONException{

        CategoryFetcher fetcher = new CategoryFetcher();
        TableWriter tableWriter = new TableWriter();

        for (Category category : testCategories) {

            JSONArray categoryResponse = fetcher.getCategoryJSON(1);

            System.out.println("Category: "+ category.toString()+" Got:\n" + categoryResponse);

            List<Product> productList = fetcher.getProductRequests(category, categoryResponse);

            System.out.println(" Got: " + productList.size() + " products");
            System.out.println(" Got: " + productList.toString() );

            ProductFetcher productFetcher = new ProductFetcher();

            int limit = 3;

            for (Product product : productList) {

                JSONArray prices = productFetcher.getProductJSON(product);
                tableWriter.addProductLine( product , prices);

                if(--limit == 0)
                    break;

            }

        }



        System.out.println("CSV: \n" + tableWriter.toString());

        WritableWorkbook excel = tableWriter.toExcel("medicinpriser.xls");
        try {
            excel.write();
            excel.close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
            assertTrue(false);
        }


    }


}