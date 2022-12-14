package com.example.vacinaapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataHelper(context: Context): SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object {
        val dbVersion = 1
        private const val dbName = "BancoDados.db"
        private const val TABELANOME = "tabela_postos"
        private const val ID = "id_local"
        private const val ID_DISTRITO_FK = "id_distrito_fk"
        private const val POSTO = "posto_saude"
        private const val DISTRITO = "distrito"
        private const val ENDERECO = "endereco"
        private const val TELEFONE = "telefone"
        private const val SEGUNDA = "segunda"
        private const val TERCA = "terca"
        private const val QUARTA = "quarta"
        private const val QUINTA = "quinta"
        private const val SEXTA = "sexta"
        private const val SABADO = "sabado"
        private const val DOMINGO = "domingo"

        //TABELA DE VACINAS
        private const val TABELA_VACINA = "tabela_vacina"
        private const val ID_VACINA = "id_vacina"             //id único da vacina
        private const val ID_POSTO = "id_posto"               //id do posto a qual a vacina pertence
                                                              // (SERVE PARA ORGANIZAR AS VACINAS EM CADA POSTO)
        private const val POSTO_NOME = "posto_nome"
        private const val DOENCA = "doenca"               //nome da doenca a qual a vacina combate
        private const val DISPONIBILIDADE = "disponibilidade" //(sim ou não)
        private const val PUBLICO = "publico"                 //descrição do púico alvo da vacina

        //TABELA DE CAMPANHAS
        private const val TABELA_CAMPANHA = "tab_campanha"
        private const val ID_CAMPANHA = "id_campanha"
        private const val DISTRITO_FK_CAMPANHA = "distrito_fk"
        private const val DISTRITO_CAMPANHA = "distrito_campanha"
        private const val ID_POSTO_CAMPANHA = "id_posto_campanha"
        private const val POSTO_NOME_CAMPANHA = "posto_nome_campanha"
        private const val NOME_CAMPANHA = "nome_campanha"
        private const val DOENCA_CAMPANHA = "doenca_campanha"
        private const val DATA = "data"                          //inclui a data de inicio e fim
        private const val HORARIO = "horario"                    //inclui o horario de inicio e fim
        private const val PUBLICO_CAMPANHA = "publico_campanha"
        private const val DETALHES = "detalhes"                  //detalhes da campanha

        //tabela usuario
        private const val TABELA_USUARIO = "tab_usuario"
        private const val ID_USUARIO = "id_usuario"              //primary key
        private const val NOME_COMPLETO = "nome_completo"
        private const val CPF = "cpf"
        private const val TELEFONE_USUARIO = "telefone_usuario"
        private const val EMAIL = "email_usuario"
        private const val NOME_USUARIO = "nome_usuario"
        private const val SENHA = "senha"

        //tabela distritos
        private const val TAB_DISTRITOS = "distrito_tab"
        private const val ID_DISTRITO = "id_distrito"
        private const val DISTRITO_NOME = "distrito_nome"
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val criaTab1 = ("CREATE TABLE $TABELANOME ($ID INTEGER PRIMARY KEY, $POSTO TEXT, $ID_DISTRITO_FK INTEGER,$DISTRITO TEXT, $ENDERECO TEXT, $TELEFONE TEXT, $SEGUNDA TEXT, $TERCA TEXT, $QUARTA TEXT, $QUINTA TEXT, $SEXTA TEXT, $SABADO TEXT, $DOMINGO TEXT, FOREIGN KEY($ID_DISTRITO_FK) REFERENCES $TAB_DISTRITOS($ID_DISTRITO))")

        db?.execSQL(criaTab1)

        val criaTab2 = (
                "CREATE TABLE $TABELA_VACINA ($ID_VACINA INTEGER PRIMARY KEY, $ID_POSTO INTEGER NOT NULL, $POSTO_NOME TEXT, $DOENCA TEXT, $DISPONIBILIDADE TEXT, $PUBLICO TEXT)"
                )
        db?.execSQL(criaTab2)

        val criaTab3 = (
                "CREATE TABLE $TABELA_CAMPANHA ($ID_CAMPANHA INTEGER PRIMARY KEY, $DISTRITO_FK_CAMPANHA INTEGER, $DISTRITO_CAMPANHA TEXT NOT NULL, $ID_POSTO_CAMPANHA INTEGER, $POSTO_NOME_CAMPANHA TEXT,$NOME_CAMPANHA TEXT, $DOENCA_CAMPANHA TEXT, $DATA TEXT, $HORARIO TEXT, $PUBLICO_CAMPANHA TEXT, $DETALHES TEXT, FOREIGN KEY($DISTRITO_FK_CAMPANHA) REFERENCES $TAB_DISTRITOS($ID_DISTRITO))"
                )
        db?.execSQL(criaTab3)

        val criaTabUsuario = (
                "CREATE TABLE $TABELA_USUARIO ($ID_USUARIO INTEGER PRIMARY KEY, $NOME_COMPLETO TEXT, $CPF TEXT, $TELEFONE_USUARIO TEXT, $EMAIL TEXT, $NOME_USUARIO TEXT, $SENHA TEXT)"
                )
        db?.execSQL(criaTabUsuario)

        val criaTabDistrito = (
                "CREATE TABLE $TAB_DISTRITOS ($ID_DISTRITO INTEGER PRIMARY KEY, $DISTRITO_NOME)"
                )
        db?.execSQL(criaTabDistrito)

        val q1 =(
            "INSERT INTO tabela_postos(id_local, posto_saude, id_distrito_fk, distrito, endereco, telefone, segunda, terca, quarta, quinta, sexta, sabado, domingo) "+
                    "VALUES " +
                    "(1, 'POSTO DE SAÚDE DE GALENA', 1,'Galena', 'Zona rural - Galena, Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +


                    "(2, 'POSTO DE SAÚDE DE PONTE FIRME',2, 'Ponte Firme', 'R. Princesa Isabel - Pte. Firme, Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(3, 'PSF AEROPORTO', 3, 'Presidente Olegário', 'R. Pres. J K, 690 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112885', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(4, 'UBS ANDORINHAS',3, 'Presidente Olegário', 'R. Saturnino Xavier Rosa, 661 - Pres. Olegário, MG, 38750-000', "+
                    "'(38) 38112136', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(5, 'UBS BILÉ GODINHO',3, 'Presidente Olegário', 'R. Ilídio Araújo, 314 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112136', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(6, 'UBS DERCINA MARIA ANDRÉ',4, 'Santiago de Minas', 'Estrada Pres. Olegário  - Zona Rural - Presidente Olegário, MG - CEP: 38750000', "+
                    "'Não informado', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(7, 'UBS MATEUS CAIXETA',3, 'Presidente Olegário', 'R. Isabel De Souza Vasconcelos, bairro Mateus caixeta, 115 - Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(8, 'UBS PLANALTO',3, 'Presidente Olegário', 'R. Pimpim Moreira, bairro Planalto, 915 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112205', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(9, 'UBS ZONA RURAL',3, 'Presidente Olegário', 'Rua Brejo Alegre, Planalto - Pres. Olegário - MG, 38750-000', "+
                    "'(34) 38112973', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00'," +
                    "'Fechado', 'Fechado')"
            )
        db?.execSQL(q1)

        val q2 = (
                "INSERT INTO tabela_vacina(id_vacina, id_posto, posto_nome, doenca, disponibilidade, publico)"+
                    "VALUES" +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Tuberculose', 'Sim', 'Crianças menores de 5 anos')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Difteria', 'Sim', 'Crianças de até 2 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Pneumocócica', 'Sim', 'Crinças de até 2 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Poliomelite', 'Sim', 'Crianças menores de 2 a 4 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Diarreia causada por rotavírus', 'Sim', 'Crianças a partir de 2 meses a 4 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Tétano', 'Sim', 'A partir dos 15 meses')," +
                        "(NULL, 1, 'POSTO DE SAÚDE DE GALENA', 'Covid-19', 'Sim', 'Maiores de 40 anos')," +


                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Difteria', 'Sim', 'Crianças de até 2 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Pneumocócica', 'Sim', 'Crinças de até 2 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Poliomelite', 'Sim', 'Crianças menores de 2 a 4 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Diarreia causada por rotavírus', 'Sim', 'Crianças a partir de 2 meses a 4 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Tétano', 'Sim', 'A partir dos 15 meses')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Covid-19', 'Sim', 'Maiores de 40 anos')," +
                        "(NULL, 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Tuberculose', 'Sim', 'Crianças menores de 5 anos')," +

                        "(NULL, 3, 'PSF AEROPORTO', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 3, 'PSF AEROPORTO', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 3, 'PSF AEROPORTO', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 3, 'PSF AEROPORTO', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 3, 'PSF AEROPORTO', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 3, 'PSF AEROPORTO', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 4, 'UBS ANDORINHAS', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 4, 'UBS ANDORINHAS', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 4, 'UBS ANDORINHAS', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 4, 'UBS ANDORINHAS', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 4, 'UBS ANDORINHAS', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 4, 'UBS ANDORINHAS', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 5, 'UBS BILÉ GODINHO', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 5, 'UBS BILÉ GODINHO', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 5, 'UBS BILÉ GODINHO', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 5, 'UBS BILÉ GODINHO', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 5, 'UBS BILÉ GODINHO', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 5, 'UBS BILÉ GODINHO', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 6, 'UBS DERCINA MARIA ANDRÉ', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 7, 'UBS MATEUS CAIXETA', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 8, 'UBS PLANALTO', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 8, 'UBS PLANALTO', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 8, 'UBS PLANALTO', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 8, 'UBS PLANALTO', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 8, 'UBS PLANALTO', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 8, 'UBS PLANALTO', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')," +

                        "(NULL, 9, 'UBS ZONA RURAL', 'Difteria', 'Sim', 'Adolescentes')," +
                        "(NULL, 9, 'UBS ZONA RURAL', 'Febre Amarela', 'Sim', 'Bebês a partir de 9 meses, pessoas de 11 a 59 anos')," +
                        "(NULL, 9, 'UBS ZONA RURAL', 'Hepatite A', 'Sim', 'Crianças de 15 meses')," +
                        "(NULL, 9, 'UBS ZONA RURAL', 'Hepatite B', 'Sim', 'Desde recém-nascidos até pessoas com 59 anos')," +
                        "(NULL, 9, 'UBS ZONA RURAL', 'HPV', 'Sim', 'Meninas de 9 a 14 anos e meninos de 11 a 14 anos')," +
                        "(NULL, 9, 'UBS ZONA RURAL', 'Meningite', 'Sim', 'Crianças de 3 meses até 10 anos de idade')"
                )
        db?.execSQL(q2)

        val q3 = (
                "INSERT INTO ${TABELA_CAMPANHA}(${ID_CAMPANHA}, $DISTRITO_FK_CAMPANHA, ${DISTRITO_CAMPANHA}, ${ID_POSTO_CAMPANHA}, ${POSTO_NOME_CAMPANHA}, ${NOME_CAMPANHA}, ${DOENCA_CAMPANHA}, ${DATA}, ${HORARIO}, ${PUBLICO_CAMPANHA}, ${DETALHES}) " +
                        "VALUES" +
                        "(NULL, 1, 'Galena', 1, 'POSTO DE SAÚDE DE GALENA','Campanha contra gripe', 'Gripe - H1N1', '17/03/2022 até 20/03/2022', '08:00 às 14:00'," +
                        " 'Crianças menores de 5 anos, idosos com mais de 59 anos e profissionais da área da saúde', " +
                        "'Campanha sazonal de combate a gripe na região de Galena. Lembre-se de levar o cartão do SUS, o cartão de vacinação e um documento de identidade'), "+
                        "(NULL, 1, 'Galena', 1, 'POSTO DE SAÚDE DE GALENA', 'Campanha contra COVID-19', 'COVID-19', '20/07/2022 até 23/07/2022', '09:00 às 15:00'," +
                        "'Adultos com mais de 40 anos que já tomaram a terceira dose da vacina', " +
                        "'No dia 23/07 o horário de atendimento será das 8:00 até as 14:00.\nNos outros dias o horário será normal.'), "+
                        "(NULL, 4, 'Santiago de Minas', 6, 'UBS DERCINA MARIA ANDRÉ', 'Campanha contra Tétano - Reforço', 'Tétano', '17/05/2022', '08:00 às 14:00'," +
                        "'Pessoas que já completaram 10 anos desde a última vacina contra o tétano.', " +
                        "'Lembre-se de levar o cartão do SUS, o cartão de vacinação e um documento de identidade.'), "+
                        "(NULL, 3, 'Presidente Olegário', 8, 'UBS PLANALTO', 'Campanha contra Febre amarela', 'Febre Amarela', '04/03/2022 a 06/03/2022', '08:30 às 15:00'," +
                        "'Público dos 4 anos de idade a 59 anos', " +
                        "'Crianças, ao completarem 4 anos de idade, devem receber 1 (uma) dose de reforço; Pessoas de 5 a 59 anos de idade, não vacinadas ou sem comprovante de vacinação, devem receber 1 (uma) dose; Pessoas que receberam apenas 1 (uma) dose da vacina antes de completarem 5 anos de idade devem receber 1 (uma) dose de reforço'), "+
                        "(NULL, 2, 'Ponte Firme', 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Campanha contra gripe', 'Gripe - H1N1', '17/03/2022 até 20/03/2022', '08:00 às 14:00'," +
                        " 'Crianças menores de 5 anos, idosos com mais de 59 anos e profissionais da área da saúde', " +
                        "'Campanha sazonal de combate a gripe na região de Ponte Firme. Lembre-se de levar o cartão do SUS, o cartão de vacinação e um documento de identidade'), "+
                        "(NULL, 2, 'Ponte Firme', 2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Campanha contra COVID-19', 'COVID-19', '20/07/2022 até 23/07/2022', '09:00 às 15:00'," +
                        "'Adultos com mais de 40 anos que já tomaram a terceira dose da vacina', " +
                        "'No dia 23/07 o horário de atendimento será das 8:00 até as 14:00.\nNos outros dias o horário será normal.'), "+
                        "(NULL, 3,'Presidente Olegário', 3, 'PSF AEROPORTO', 'Campanha contra gripe', 'Gripe - H1N1', '17/03/2022 até 20/03/2022', '08:00 às 14:00'," +
                        " 'Crianças menores de 5 anos, idosos com mais de 59 anos e profissionais da área da saúde', " +
                        "'Campanha sazonal de combate a gripe na região de Presidente Olegário. Lembre-se de levar o cartão do SUS, o cartão de vacinação e um documento de identidade'), "+
                        "(NULL, 3, 'Presidente Olegário', 4, 'UBS ANDORINHAS', 'Campanha contra COVID-19', 'COVID-19', '20/07/2022 até 23/07/2022', '09:00 às 15:00'," +
                        "'Adultos com mais de 40 anos que já tomaram a terceira dose da vacina', " +
                        "'No dia 23/07 o horário de atendimento será das 8:00 até as 14:00.\nNos outros dias o horário será normal.'), "+
                        "(NULL, 3,'Presidente Olegário', 7, 'UBS MATEUS CAIXETA', 'Campanha contra Tétano - Reforço', 'Tétano', '17/05/2022', '08:00 às 14:00'," +
                        "'Pessoas que já completaram 10 anos desde a última vacina contra o tétano.', " +
                        "'Lembre-se de levar o cartão do SUS, o cartão de vacinação e um documento de identidade.'), "+
                        "(NULL, 3,'Presidente Olegário', 9, 'UBS ZONA RURAL', 'Campanha contra HPV', 'HPV', '25/05/2022', '08:00 às 14:00'," +
                        "'Meninas de 9 a 14 anos e meninos com 11 a 14 anos', " +
                        "'Aplicação tanto da 1ª dose quanto da 2ª dose para o público'), "+
                        "(NULL, 3,'Presidente Olegário', 5, 'UBS BILÉ GODINHO', 'Campanha contra Febre amarela', 'Febre Amarela', '04/03/2022 a 06/03/2022', '08:30 às 15:00'," +
                        "'Público dos 4 anos de idade a 59 anos', " +
                        "'Crianças, ao completarem 4 anos de idade, devem receber 1 (uma) dose de reforço; Pessoas de 5 a 59 anos de idade, não vacinadas ou sem comprovante de vacinação, devem receber 1 (uma) dose; Pessoas que receberam apenas 1 (uma) dose da vacina antes de completarem 5 anos de idade devem receber 1 (uma) dose de reforço')"
                )
        db?.execSQL(q3)

        val queryUsuario = (
            "INSERT INTO $TABELA_USUARIO($ID_USUARIO, $NOME_COMPLETO, $CPF, $TELEFONE_USUARIO, $EMAIL, $NOME_USUARIO, $SENHA) VALUES (NULL, 'João Silva Sousa', '123456789-12', '(34) 98765-4321', 'joao123@email.com', 'usuario1', '12345678'), " +
                    "(NULL, 'Maria Silva Oliveira', '123456789-01', '(34) 99999-9999', 'mariaSO@email.com', 'maria', 'senha123')"
        )
        db?.execSQL(queryUsuario)

        val queryDistrito = (
                "INSERT INTO $TAB_DISTRITOS($ID_DISTRITO, $DISTRITO_NOME) VALUES (1, 'Galena'), (2, 'Ponte Firme'), (3, 'Presidente Olegário'), (4, 'Santiago de Minas')"
                )
        db?.execSQL(queryDistrito)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Usar o código abaixo para reiniciar a tabela
        db!!.execSQL("DROP TABLE IF EXISTS tabela_postos")
    }

    fun rawQuery(query: String?): Cursor {
        val db = this.writableDatabase
        val mCursor: Cursor = db.rawQuery(query, null)
        return mCursor
    }

    fun insert(id_posto: Int, posto_nome: String, doenca_nome: String, disponibilidade: String, publico: String): Long{
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_POSTO, id_posto)
        contentValues.put(POSTO_NOME, posto_nome)
        contentValues.put(DOENCA, doenca_nome)
        contentValues.put(DISPONIBILIDADE, disponibilidade)
        contentValues.put(PUBLICO, publico)

        val result: Long = db.insert(TABELA_VACINA, null, contentValues)
        return result
    }

    fun insertCampanha(id_distrito: Int, distrito_campanha: String, id_posto_campanha: Int, posto_nome_campanha: String, nome_campanha: String,
                       doenca_campanha: String, data_campanha: String, horario: String, publico_campanha: String, detalhes_campanha: String): Long{

        val db: SQLiteDatabase = this.writableDatabase
        val contentValuesCamp = ContentValues()
        contentValuesCamp.put(DISTRITO_FK_CAMPANHA, id_distrito)
        contentValuesCamp.put(DISTRITO_CAMPANHA, distrito_campanha)
        contentValuesCamp.put(ID_POSTO_CAMPANHA, id_posto_campanha)
        contentValuesCamp.put(POSTO_NOME_CAMPANHA, posto_nome_campanha)
        contentValuesCamp.put(NOME_CAMPANHA, nome_campanha)
        contentValuesCamp.put(DOENCA_CAMPANHA, doenca_campanha)
        contentValuesCamp.put(DATA, data_campanha)
        contentValuesCamp.put(HORARIO, horario)
        contentValuesCamp.put(PUBLICO_CAMPANHA, publico_campanha)
        contentValuesCamp.put(DETALHES, detalhes_campanha)

        val result: Long = db.insert(TABELA_CAMPANHA, null, contentValuesCamp)
        return result
    }

    fun deleteCampanha(_id: Int?): Boolean{
        val db = this.writableDatabase
        val success = db.delete(TABELA_CAMPANHA, "$ID_CAMPANHA = $_id", null)
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun updateCampanha(id_campanha: Int,id_distrito: Int, distrito_campanha: String, id_posto_campanha: Int, nome_posto: String, nome_campanha: String, doenca_campanha: String, data_campanha: String, horario_campanha: String, publico_campanha: String, detalhes_campanha: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DISTRITO_CAMPANHA, distrito_campanha)
        contentValues.put(DISTRITO_FK_CAMPANHA, id_distrito)
        contentValues.put(ID_POSTO_CAMPANHA, id_posto_campanha)
        contentValues.put(POSTO_NOME_CAMPANHA, nome_posto)
        contentValues.put(NOME_CAMPANHA, nome_campanha)
        contentValues.put(DOENCA_CAMPANHA, doenca_campanha)
        contentValues.put(DATA, data_campanha)
        contentValues.put(HORARIO, horario_campanha)
        contentValues.put(PUBLICO_CAMPANHA, publico_campanha)
        contentValues.put(DETALHES, detalhes_campanha)

        val success = db.update(TABELA_CAMPANHA, contentValues, "$ID_CAMPANHA = $id_campanha", null)
        db.close()
        return success

    }

    fun updateVacina(id_vacina: Int, id_posto: Int, posto_nome: String, doenca: String, disponibilidade: String, publico: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_POSTO, id_posto)
        contentValues.put(POSTO_NOME, posto_nome)
        contentValues.put(DOENCA, doenca)
        contentValues.put(DISPONIBILIDADE, disponibilidade)
        contentValues.put(PUBLICO, publico)

        val success = db.update(TABELA_VACINA, contentValues, "$ID_VACINA = $id_vacina", null)
        db.close()
        return success

    }

    fun insertUsuario(nome_completo: String, cpf: String, telefone: String, email: String, nome_usuario: String, senha: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NOME_COMPLETO, nome_completo)
        contentValues.put(CPF, cpf)
        contentValues.put(TELEFONE_USUARIO, telefone)
        contentValues.put(EMAIL, email)
        contentValues.put(NOME_USUARIO, nome_usuario)
        contentValues.put(SENHA, senha)


        val success = db.insert(TABELA_USUARIO, null, contentValues)
        return success
    }

    //a funcao só serve para os dados pessoais, não senha
    fun updateUsuarioDados(id_usuario: Int, nome_completo: String, cpf: String, telefone: String, email: String, usuario: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NOME_COMPLETO, nome_completo)
        contentValues.put(CPF, cpf)
        contentValues.put(TELEFONE_USUARIO, telefone)
        contentValues.put(EMAIL, email)
        contentValues.put(NOME_USUARIO, usuario)

        val success = db.update(TABELA_USUARIO, contentValues, "$ID_USUARIO = $id_usuario", null)
        db.close()
        return success

    }

    fun updateUsuarioSenha(id_usuario: Int, senha: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SENHA, senha)


        val success = db.update(TABELA_USUARIO, contentValues, "$ID_USUARIO = $id_usuario", null)
        db.close()
        return success

    }


}
