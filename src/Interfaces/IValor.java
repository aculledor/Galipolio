/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author aculledor
 */
public interface IValor {
    //COLORES
    public static final String NEGRO    ="\033[0;30m";
    public static final String ROJO     ="\033[0;31m";
    public static final String VERDE    ="\033[0;32m";
    public static final String AMARILLO ="\033[0;33m";
    public static final String AZUL     ="\033[0;34m";
    public static final String VIOLETA  ="\033[0;35m";
    public static final String CIAN     ="\033[0;36m";
    public static final String BLANCO   ="\033[0;37m";
    public static final String BNEGRO   ="\033[1;30m";
    public static final String BROJO    ="\033[1;31m";
    public static final String BVERDE   ="\033[1;32m";
    public static final String BAMARILLO="\033[1;33m";
    public static final String BAZUL    ="\033[1;34m";
    public static final String BVIOLETA ="\033[1;35m";
    public static final String BCIAN    ="\033[1;36m";
    public static final String BBLANCO  ="\033[1;37m";
    
    public static final String RESET    ="\033[0;00m";
    public static final String FONDO_NEGRO="\033[0;40m";
    
    //VALORES DADOS
    public static final int MINDADOS        =1;
    public static final int MAXDADOS        =6;
    public static final int MAXDADOSDOBLES  =3;
    
    //VALOR TAMAÑO TOSTRING CASILLA
    public static final int TAMANHOCASILLA=21;                  //El mayor nombre es "TELETRANSPORTE"(14) + " "(15) + "------"(6 jugadores) = --21--
    
    //NUMERO PROPIEDADES EN GRUPO
    public static final int NUMPROPGRUPOPEQUENO     =2;
    public static final int NUMPROPGRUPOGRANDE      =3;
    public static final int NUMPROPGRUPOTRANSPORTES =4;
    public static final int NUMPROPGRUPOSERVIZOS    =2;
    
    //VALORES QUE ESTÁN RELACIONADOS COS PREZOS
    public static final int NUMEROSOLARES                   =22; 
    public static final int VALORSUMATORIOPROPIEDADESINICIAL=4950;                          //SUMATORIO DOS PREZOS INICIAIS DAS PROPIEDADES
    public static final double CAPITALINICIAL=VALORSUMATORIOPROPIEDADESINICIAL/3;               //1650
    public static final double VALORSALIDA=VALORSUMATORIOPROPIEDADESINICIAL/NUMEROSOLARES;     //225
    public static final double INFLACION                    =1.05;
    public static final double INTERESDESHIPOTECAR          =1.1;
    public static final int VOLTASAPLICARINFLACION          =4;
    public static final double FACTORALQUILERTRANSPORTE     = 0.25;
    public static final double FACTORALQUILERSERVICIO       = 0.005;
    
    //VALORES COMPRA EDIFICIOS
    public static final int PISADADONOEDIFICAR          = 2; 
    public static final int MAXCASASSOLAR               = 4;
    public static final double FACTORALQUILERSOLAR      = 0.1;
    public static final double FACTORCASA               = 0.6;
    public static final double FACTORHOTEL              = 0.6;
    public static final double FACTORPISCINA            = 0.4;
    public static final double FACTORPISTADEPORTIVA     = 1.25;
    
    //VALORES ALQUILER SERVIZOS
    public static final double FACTORUNSERVIZO      = 4;
    public static final double FACTORDOUSSERVIZOS   = 10;
    
    //VALORES MOVEMENTOS AVANZADOS
    public static final int MOVRESTANTEESFINXE      = 3;
    public static final int MOVRESTANTESOMBREIRO    = 3;
    public static final int MOVRESTANTECOCHE        = 4;
    public static final int TURNOSQUIETOCOCHE       = 3;  //CUENTA EL TURNO ACTUAL
    
    //VALORES ALQUILER POR EDIFICIO
    public static final int ALQUILERUNACASA         = 5;
    public static final int ALQUILERDOSCASAS        = 15;
    public static final int ALQUILERTRESCASAS       = 35;
    public static final int ALQUILERCUATROCASAS     = 50;
    public static final int ALQUILERHOTEL           = 70;
    public static final int ALQUILERPISCINA         = 25;
    public static final int ALQUILERPISTADEPORTIVA  = 25;
    public static final int ALQUILERCONTODOGRUPO    = 2;
    
    //PRECIOS CASILLAS
    public static final double VALORPROPG1= 50;
    public static final double VALORPROPG2= 100;
    public static final double VALORPROPG3= 150;
    public static final double VALORPROPG4= 200;
    public static final double VALORPROPG5= 250;
    public static final double VALORPROPG6= 300;
    public static final double VALORPROPG7= 350;
    public static final double VALORPROPG8= 400;
    public static final double VALORIMPOSTOBARATO=   VALORSALIDA/2;
    public static final double VALORIMPOSTOCARO =    VALORSALIDA;
    public static final double VALORSERVIZO     =    VALORSALIDA*0.75;
    public static final double VALORTRANSPORTE  =    VALORSALIDA;
    public static final double VALORSALIRCARCEL =    VALORSALIDA*0.5;
    
    
    public static final double VALORGRUPO1=VALORPROPG1*NUMPROPGRUPOPEQUENO;
    public static final double VALORGRUPO2=VALORPROPG2*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO3=VALORPROPG3*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO4=VALORPROPG4*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO5=VALORPROPG5*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO6=VALORPROPG6*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO7=VALORPROPG7*NUMPROPGRUPOGRANDE;
    public static final double VALORGRUPO8=VALORPROPG8*NUMPROPGRUPOPEQUENO;
    public static final double VALORGRUPOTRANS=VALORTRANSPORTE*NUMPROPGRUPOTRANSPORTES;
    public static final double VALORGRUPOSERV=VALORSERVIZO*NUMPROPGRUPOSERVIZOS;
    
    //PRECIOS CARTAS
    public static final double VALORSORTE9      =60;
    public static final double VALORCOMUNIDADE10=50;

}
