package iphan.pibex.igarassu.ifpe.edu.br;

import android.app.Application;

/**
 * Created by AlunoIFPE on 20/03/2017.
 */

public class CustomApplication extends Application{
    private Location[] locations;

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }


    void populateLocations() {
        // TODO: load the date from a file or and database

        Object[][] rawData = new Object[][]{
                {"Igreja e Convento Franciscanos de Santo Antônio", -7.832511, -34.905131, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."}, //1

                {"Secretária de Turismo", -7.8337595, -34.9054833, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},//2

                {"Empresa de Urbanização de Igarassu(URBI)", -7.834452, -34.905451, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //3

                {"Câmara Municipal", -7.835233, -34.906164, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //4

                {"Ruínas da Igreja da Misericórdia", -7.8358037, -34.9073714, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},//5

                {"Casa do Artesão", -7.834902, -34.906872, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //6

                {"Casa do Patrimônio em Igarassu/Iphan(Sobrado do Imperador)", -7.834733, -34.906740, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //7

                {"Recolhimento e Igreja do Sagrado Coração de Jesus", -7.834387, -34.906491, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //8

                {"Museu Histórico", -7.834078, -34.906410, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //9

                {"Igreja de São Cosme e São Damião", -7.834018, -34.906148, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //10

                {"Casa Paroquial", -7.833618, -34.906010, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //11

                {"CVT - Centro Vocacional Tecnológico", -7.833499, -34.905951, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //12

                {"Biblioteca Municipal", -7.833318, -34.905844, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //13

                {"Loja Maçônica", -7.832854, -34.906450, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},    //14
                {"Secretária de Planejamento, Meio Ambiente e Patrimônio Histórico(SEPLAMAPH)", -7.832889, -34.906573, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
                //15
                {"Prefeitura Municipal", -7.833217, -34.906572, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
                //16
                {"Igreja de Nossa Senhora do Livramento", -7.833169, -34.906673, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
                //17
                {"Centro de Artes e Cultura", -7.832004, -34.908098, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
                //18
                {"Igreja de São Sebastião", -7.831667, -34.908622, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
                //19
                {"Secretária de Obras", -7.8316536, -34.9091987, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."}//20

        };

        this.locations = new Location[rawData.length];

        for (int i = 0; i < rawData.length; i++) {
            locations[i] = new Location((String) rawData[i][0], (Double) rawData[i][1], (Double) rawData[i][2], (String) rawData[i][3]);
        }
    }

}
