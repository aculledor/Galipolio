/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartesTablero;

import Interfaces.*;
import Edificaciones.*;
import Excepciones.*;
import PartesCarta.*;
import Usuario.*;
import java.util.ArrayList;
import PartesCasilla.*;
import static java.lang.Math.pow;
import java.util.Random;

/**
 *
 * @author eci-toshiba
 */
public class Tablero {
        //ATRIBUTOS
    private final ArrayList<ArrayList<Casilla>> casillas; 
    private final ArrayList<Jugador>            listaXogadores;
    private final ArrayList<Edificio>           listaEdificios;
    private final ArrayList<Grupo>              listaGrupos; 
    private final ArrayList<Suerte>             baraxaCartasSorte;
    private final ArrayList<CajaComunidad>      baraxaCartasComunidade;
    private Jugador laBanca; 
    //Atributos Turno                                
    private int ronda;
    private int turnoJugador;       //o turnose toca
    private int vecesAumentoSolares;//as veces que aumentou o prezo dos solares           
    private int numDobles;          //nume de dados dobres que sacaches neste turno
    private boolean podeLanzarDados;//se neste turno podes lanzar dados
    private boolean comprouseCoche; //se o coche comprou en movemento avanzado neste turno
    private boolean intento;        //se intentaches sair do carcere lanzando dados este turno
    
    public Tablero(){     
        comprouseCoche  =false;
        podeLanzarDados =true;
        intento         =false;
        ronda       =0;
        turnoJugador=0;
        numDobles   =0;
        vecesAumentoSolares=IValor.VOLTASAPLICARINFLACION;
        
        laBanca         =new Jugador(this);        //laBanca
        listaXogadores  =new ArrayList<>();
        listaEdificios  =new ArrayList<>();
        casillas        =new ArrayList<>();         //ArrayList con las casilla
        //Calles del tablero
        casillas.add(new ArrayList<>());                    //Calle 0, de SALIDA a CARCEL 
        casillas.add(new ArrayList<>());                    //Calle 1, de CARCEL a PARKING
        casillas.add(new ArrayList<>());                    //Calle 2, de PARKING a IR A CARCEL
        casillas.add(new ArrayList<>());                    //Calle 3, de IR A CARCEL a SALIDA 
        //ARRAYLISTS DE CARTAS
        baraxaCartasSorte       =new ArrayList<>();
        baraxaCartasComunidade  =new ArrayList<>();
        //Creamos los diferentes listaGrupos por colores
        Grupo g1=new Grupo(IValor.AMARILLO, IValor.VALORGRUPO1,this,IDescripcion.NOMGRUPO1);                 //Grupo 1, 2 primeras propiedades
        Grupo g2=new Grupo(IValor.ROJO,     IValor.VALORGRUPO2,this,IDescripcion.NOMGRUPO2);                 //Grupo 2, propiedades A6,A8,A9
        Grupo g3=new Grupo(IValor.CIAN,     IValor.VALORGRUPO3,this,IDescripcion.NOMGRUPO3);                 //Grupo 3, propiedades A11,A13,A14
        Grupo g4=new Grupo(IValor.VIOLETA,  IValor.VALORGRUPO4,this,IDescripcion.NOMGRUPO4);                 //Grupo 4, propiedades A16,A18,A19
        Grupo g5=new Grupo(IValor.AZUL,     IValor.VALORGRUPO5,this,IDescripcion.NOMGRUPO5);                 //Grupo 5, propiedades A21,A23,A24
        Grupo g6=new Grupo(IValor.VERDE,    IValor.VALORGRUPO6,this,IDescripcion.NOMGRUPO6);                 //Grupo 6, propiedades A26,A27,A29
        Grupo g7=new Grupo(IValor.BNEGRO,   IValor.VALORGRUPO7,this,IDescripcion.NOMGRUPO7);                 //Grupo 7, propiedades A31,A32,A34
        Grupo g8=new Grupo(IValor.BROJO,    IValor.VALORGRUPO8,this,IDescripcion.NOMGRUPO8);                 //Grupo 8, propiedades A37,A39
        Grupo gt=new Grupo(IValor.NEGRO,    IValor.VALORGRUPOTRANS,this,IDescripcion.NOMGRUPO9);             //Grupo transportes
        Grupo gs=new Grupo(IValor.NEGRO,    IValor.VALORGRUPOSERV, this,IDescripcion.NOMGRUPO10);            //Grupo servicios
        //Creación das 40 casillas
        Especial  A0            =new Especial(  this, IDescripcion.NOMCAS1,0);                  
        Propiedad A1            =new Solar(IValor.VALORPROPG1,  this, IDescripcion.NOMCAS2, g1,1);                       
        CasillaComunidade A2    =new CasillaComunidade(baraxaCartasComunidade, this, IDescripcion.NOMCAS3,2);          
        Propiedad A3            =new Solar(IValor.VALORPROPG1,  this, IDescripcion.NOMCAS4, g1,3);                       
        Impuesto  A4            =new Impuesto(IValor.VALORIMPOSTOBARATO, this, IDescripcion.NOMCAS5,4);                       
        Propiedad A5            =new Transporte(this, IDescripcion.NOMCAS6,gt,5);                       
        Propiedad A6            =new Solar(IValor.VALORPROPG2,  this, IDescripcion.NOMCAS7, g2,6);                       
        CasillaSorte      A7    =new CasillaSorte(baraxaCartasSorte,  this, IDescripcion.NOMCAS8,7);                       
        Propiedad A8            =new Solar(IValor.VALORPROPG2,  this, IDescripcion.NOMCAS9, g2,8);                       
        Propiedad A9            =new Solar(IValor.VALORPROPG2,  this, IDescripcion.NOMCAS10,g2,9);                       
        Especial  A10           =new Especial(  this, IDescripcion.NOMCAS11,10);                      
        Propiedad A11           =new Solar(IValor.VALORPROPG3,  this, IDescripcion.NOMCAS12,g3,11);                      
        Propiedad A12           =new Servicio(  this, IDescripcion.NOMCAS13,gs,12);                     
        Propiedad A13           =new Solar(IValor.VALORPROPG3,  this, IDescripcion.NOMCAS14,g3,13);                     
        Propiedad A14           =new Solar(IValor.VALORPROPG3,  this, IDescripcion.NOMCAS15,g3,14);                     
        Propiedad A15           =new Transporte(this, IDescripcion.NOMCAS16,gt,15);                     
        Propiedad A16           =new Solar(IValor.VALORPROPG4,  this, IDescripcion.NOMCAS17,g4,16);                     
        CasillaComunidade A17   =new CasillaComunidade(baraxaCartasComunidade,this, IDescripcion.NOMCAS18,17);                     
        Propiedad A18           =new Solar(IValor.VALORPROPG4,  this, IDescripcion.NOMCAS19,g4,18);                     
        Propiedad A19           =new Solar(IValor.VALORPROPG4,  this, IDescripcion.NOMCAS20,g4,19);                     
        Accion    A20           =new Accion(    this, IDescripcion.NOMCAS21,20);                     
        Propiedad A21           =new Solar(IValor.VALORPROPG5,  this, IDescripcion.NOMCAS22,g5,21);                     
        CasillaSorte      A22   =new CasillaSorte(baraxaCartasSorte,  this, IDescripcion.NOMCAS23,22);                     
        Propiedad A23           =new Solar(IValor.VALORPROPG5,  this, IDescripcion.NOMCAS24,g5,23);                     
        Propiedad A24           =new Solar(IValor.VALORPROPG5,  this, IDescripcion.NOMCAS25,g5,24);                     
        Propiedad A25           =new Transporte(this, IDescripcion.NOMCAS26,gt,25);                     
        Propiedad A26           =new Solar(IValor.VALORPROPG6,  this, IDescripcion.NOMCAS27,g6,26);                     
        Propiedad A27           =new Solar(IValor.VALORPROPG6,  this, IDescripcion.NOMCAS28,g6,27);                     
        Propiedad A28           =new Servicio(  this, IDescripcion.NOMCAS29,gs,28);                     
        Propiedad A29           =new Solar(IValor.VALORPROPG6,  this, IDescripcion.NOMCAS30,g6,29);                     
        Accion    A30           =new Accion(    this, IDescripcion.NOMCAS31,30);                  
        Propiedad A31           =new Solar(IValor.VALORPROPG7,  this, IDescripcion.NOMCAS32,g7,31);                  
        Propiedad A32           =new Solar(IValor.VALORPROPG7,  this, IDescripcion.NOMCAS33,g7,32);                  
        CasillaComunidade A33   =new CasillaComunidade(baraxaCartasComunidade, this, IDescripcion.NOMCAS34,33);                  
        Propiedad A34           =new Solar(IValor.VALORPROPG7,  this, IDescripcion.NOMCAS35,g7,34);                  
        Propiedad A35           =new Transporte(this, IDescripcion.NOMCAS36,gt,35);                  
        CasillaSorte      A36   =new CasillaSorte(baraxaCartasSorte,  this, IDescripcion.NOMCAS37,36);                  
        Propiedad A37           =new Solar(IValor.VALORPROPG8,  this, IDescripcion.NOMCAS38,g8,37);                  
        Impuesto  A38           =new Impuesto(IValor.VALORIMPOSTOCARO,this, IDescripcion.NOMCAS39,38);                  
        Propiedad A39           =new Solar(IValor.VALORPROPG8,  this, IDescripcion.NOMCAS40,g8,39);                  

        casillas.get(0).add(A0);                       //SALIDA
        casillas.get(0).add(A1);                       
        casillas.get(0).add(A2);                       
        casillas.get(0).add(A3);                       
        casillas.get(0).add(A4);                       
        casillas.get(0).add(A5);                       
        casillas.get(0).add(A6);                       
        casillas.get(0).add(A7);                       
        casillas.get(0).add(A8);                       
        casillas.get(0).add(A9);                       
        casillas.get(0).add(A10);                       //CARCEL

        casillas.get(1).add(A10);                       //CARCEL
        casillas.get(1).add(A11);                       
        casillas.get(1).add(A12);                       
        casillas.get(1).add(A13);                       
        casillas.get(1).add(A14);                       
        casillas.get(1).add(A15);                       
        casillas.get(1).add(A16);                       
        casillas.get(1).add(A17);                       
        casillas.get(1).add(A18);                       
        casillas.get(1).add(A19);                       
        casillas.get(1).add(A20);                       //PARKING

        casillas.get(2).add(A20);                       //PARKING
        casillas.get(2).add(A21);                       
        casillas.get(2).add(A22);                       
        casillas.get(2).add(A23);                       
        casillas.get(2).add(A24);                       
        casillas.get(2).add(A25);                       
        casillas.get(2).add(A26);                       
        casillas.get(2).add(A27);                       
        casillas.get(2).add(A28);                       
        casillas.get(2).add(A29);                       //IR A CARCEL
        casillas.get(2).add(A30);   
    
        casillas.get(3).add(A30);                       //IR A CARCEL
        casillas.get(3).add(A31);                       
        casillas.get(3).add(A32);                       
        casillas.get(3).add(A33);                        
        casillas.get(3).add(A34);                       
        casillas.get(3).add(A35);                       
        casillas.get(3).add(A36);                       
        casillas.get(3).add(A37);                       
        casillas.get(3).add(A38);                       
        casillas.get(3).add(A39);                       
        casillas.get(3).add(A0);                          //SALIDA
        
        g1.getPropiedadesGrupo().add(A1);
        g1.getPropiedadesGrupo().add(A3);
        g2.getPropiedadesGrupo().add(A6);
        g2.getPropiedadesGrupo().add(A8);
        g2.getPropiedadesGrupo().add(A9);
        g3.getPropiedadesGrupo().add(A11);
        g3.getPropiedadesGrupo().add(A13);
        g3.getPropiedadesGrupo().add(A14);
        g4.getPropiedadesGrupo().add(A16);
        g4.getPropiedadesGrupo().add(A18);
        g4.getPropiedadesGrupo().add(A19);
        g5.getPropiedadesGrupo().add(A21);
        g5.getPropiedadesGrupo().add(A23);
        g5.getPropiedadesGrupo().add(A24);
        g6.getPropiedadesGrupo().add(A26);
        g6.getPropiedadesGrupo().add(A27);
        g6.getPropiedadesGrupo().add(A29);
        g7.getPropiedadesGrupo().add(A31);
        g7.getPropiedadesGrupo().add(A32);
        g7.getPropiedadesGrupo().add(A34);
        g8.getPropiedadesGrupo().add(A37);
        g8.getPropiedadesGrupo().add(A39);
        gt.getPropiedadesGrupo().add(A5);
        gt.getPropiedadesGrupo().add(A15);
        gt.getPropiedadesGrupo().add(A25);
        gt.getPropiedadesGrupo().add(A35);
        gs.getPropiedadesGrupo().add(A12);
        gs.getPropiedadesGrupo().add(A28);
        
        
        
        
        //Hashmap con los listaGrupos por colores y orden de precio
        listaGrupos = new ArrayList<>();                         
        listaGrupos.add(g1);                     
        listaGrupos.add(g2);
        listaGrupos.add(g3);
        listaGrupos.add(g4);
        listaGrupos.add(g5);
        listaGrupos.add(g6);
        listaGrupos.add(g7);
        listaGrupos.add(g8);
        listaGrupos.add(gs);
        listaGrupos.add(gt); 
        

        casillas.forEach((i) -> {      
            i.forEach((c)->{
                if(c instanceof Propiedad){
                    laBanca.getPropiedades().add((Propiedad) c);
                }
            });
        });
        
        Suerte Sorte1=new Suerte(this, IDescripcion.CARTASORTE1, A8);
        Suerte Sorte2=new Suerte(this, IDescripcion.CARTASORTE2, A25);
        Suerte Sorte3=new Suerte(this, IDescripcion.CARTASORTE3, A10);
        Suerte Sorte4=new Suerte(this, IDescripcion.CARTASORTE4, IValor.VALORSALIDA/2, false);
        Suerte Sorte5=new Suerte(this, IDescripcion.CARTASORTE5, IValor.VALORSALIDA, false);
        Suerte Sorte6=new Suerte(this, IDescripcion.CARTASORTE6, -IValor.VALORSALIDA/2, false);
        Suerte Sorte7=new Suerte(this, IDescripcion.CARTASORTE7, -IValor.VALORSALIDA, false);
        Suerte Sorte8=new Suerte(this, IDescripcion.CARTASORTE8, -IValor.VALORSALIDA*1.5, false);
        Suerte Sorte9=new Suerte(this, IDescripcion.CARTASORTE9, IValor.VALORSORTE9, true);  //LISTO
        Suerte Sorte10=new Suerte(this,IDescripcion.CARTASORTE10, 0, false);    //0 porque é un valor especial para a funcion
        
        baraxaCartasSorte.add(Sorte1);
        baraxaCartasSorte.add(Sorte2);
        baraxaCartasSorte.add(Sorte3);
        baraxaCartasSorte.add(Sorte4);
        baraxaCartasSorte.add(Sorte5);
        baraxaCartasSorte.add(Sorte6);
        baraxaCartasSorte.add(Sorte7);
        baraxaCartasSorte.add(Sorte8);
        baraxaCartasSorte.add(Sorte9);
        baraxaCartasSorte.add(Sorte10);
        
        CajaComunidad Comunidade1=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE1, A16);
        CajaComunidad Comunidade2=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE2, A31);
        CajaComunidad Comunidade3=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE3, A0);
        CajaComunidad Comunidade4=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE4, A10);
        CajaComunidad Comunidade5=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE5, IValor.VALORSALIDA*0.75, false);
        CajaComunidad Comunidade6=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE6, IValor.VALORSALIDA*1.25, false);
        CajaComunidad Comunidade7=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE7, -IValor.VALORSALIDA*0.75, false);
        CajaComunidad Comunidade8=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE8, -IValor.VALORSALIDA*1.25, false);
        CajaComunidad Comunidade9=new CajaComunidad(this, IDescripcion.CARTACOMUNIDADE9, -IValor.VALORSALIDA*1.5, false);
        CajaComunidad Comunidade10=new CajaComunidad(this,IDescripcion.CARTACOMUNIDADE10,IValor.VALORCOMUNIDADE10, true);
        
        
        baraxaCartasComunidade.add(Comunidade1);
        baraxaCartasComunidade.add(Comunidade2);
        baraxaCartasComunidade.add(Comunidade3);
        baraxaCartasComunidade.add(Comunidade4);
        baraxaCartasComunidade.add(Comunidade5);
        baraxaCartasComunidade.add(Comunidade6);
        baraxaCartasComunidade.add(Comunidade7);
        baraxaCartasComunidade.add(Comunidade8);
        baraxaCartasComunidade.add(Comunidade9);
        baraxaCartasComunidade.add(Comunidade10);
    }

        //GETTERS Y SETTERS
    public Jugador getLaBanca()                         {return laBanca;}
    
    public ArrayList<ArrayList<Casilla>> getCasillas()  {return casillas;}

    public ArrayList<Edificio>           getEdificios() {return listaEdificios;}

    public ArrayList<Grupo>              getGrupos()          {return listaGrupos;}
    
    public ArrayList<Jugador>            getJugadores()       {return listaXogadores;}

    public ArrayList<Suerte>             getCartasSorte()     {return baraxaCartasSorte; }

    public ArrayList<CajaComunidad>      getCartasComunidade(){return baraxaCartasComunidade; }
    
    
    public int getNumDobles()                               { return numDobles; }

    public int getTurnoJugador()                            { return turnoJugador; }

    public int getRonda()                                   { return ronda; }
    
    public int getVecesAumentoSolares()                     { return (vecesAumentoSolares/4)-1; }

    public boolean isPodeLanzarDados()                      { return podeLanzarDados; }

    public boolean isIntento()                              { return intento; }

    public boolean isComprouseCoche()                       { return comprouseCoche; }
    
    public void setComprouseCoche(boolean comprouseCoche)   { this.comprouseCoche   = comprouseCoche; }

    public void setNumDobles(int numDobles)                 { this.numDobles        = numDobles; }

    public void setPodeLanzarDados(boolean podeLanzarDados) { this.podeLanzarDados  = podeLanzarDados; }

    public void setIntento(boolean intento)                 { this.intento          = intento; }
    
    public Jugador getJugadorActual()                       { return listaXogadores.get(turnoJugador); }
    
    
    
    public Casilla buscarCasilla(String nombreCasilla) throws DatoIncorrectoException{ //No se puede usar forEach con return al parecer
        for(ArrayList<Casilla> fila : casillas){ for(Casilla c : fila){ if(nombreCasilla.equalsIgnoreCase(c.getNombre())) {return c;} } }
        throw new DatoIncorrectoException("Non se atopou a casilla: "+IValor.BROJO+nombreCasilla+IValor.RESET);
    }
   
    public ArrayList<Integer> barajarCartas(String tipoBaraja){
        ArrayList<Integer> toret=new ArrayList<>();
        boolean exit;
        int aux,numCartas=0;
        Random rn=new Random();
        if(tipoBaraja.equalsIgnoreCase("suerte"))               { numCartas=baraxaCartasSorte.size();       }
        else if(tipoBaraja.equalsIgnoreCase("cajacomunidad"))   { numCartas=baraxaCartasComunidade.size();  }
        for(int i=0;i<numCartas;i++){ 
            do{
                exit=true;
                aux = (rn.nextInt(numCartas)+1);
                for (int auxiliar : toret) {
                    if(aux==auxiliar){
                        exit=false;
                        break;
                    }
                }
            }while(!exit);
            toret.add(aux);
        }
        return toret;
    }
    
    public String resetTurno(boolean bancarrota){
        String toret    ="";
        numDobles       =0;
        podeLanzarDados =true;
        intento         =false;
        comprouseCoche  =false;
        
        if(!bancarrota)                         { turnoJugador++; }
        if(turnoJugador==listaXogadores.size()) { turnoJugador=0; ronda++; }
        if(listaXogadores.size()>1){
            toret="\nTurno de: " + listaXogadores.get(turnoJugador).getNombre()+".\nModo de movemento actual: ";
            if(listaXogadores.get(turnoJugador).getAvatar().isModoAvanzado()){toret+="Avanzado.\n";}
            else {toret+="Normal.\n";}
                
            if(listaXogadores.get(turnoJugador).getAvatar().isEncarcelado()){ listaXogadores.get(turnoJugador).getAvatar().aumentarNumTurnosCarcel(); }
            if(listaXogadores.get(turnoJugador).getAvatar() instanceof Coche){
                ((Coche) listaXogadores.get(turnoJugador).getAvatar()).setMovementosRestantesCoche(IValor.MOVRESTANTECOCHE);
                if(((Coche)  listaXogadores.get(turnoJugador).getAvatar()).getSinTurnoCoche()>0){
                    ((Coche) listaXogadores.get(turnoJugador).getAvatar()).reducirSinTurnoCoche();
                    if(((Coche)listaXogadores.get(turnoJugador).getAvatar()).getSinTurnoCoche()!=0){
                        podeLanzarDados=false;
                        intento=true;
                    }
                }
            }
            else if(listaXogadores.get(turnoJugador).getAvatar() instanceof Esfinge){
                ((Esfinge) listaXogadores.get(turnoJugador).getAvatar()).setMovementosRestantesEsfinxe(IValor.MOVRESTANTEESFINXE);
            }
            else if(listaXogadores.get(turnoJugador).getAvatar() instanceof Sombrero){
                ((Sombrero) listaXogadores.get(turnoJugador).getAvatar()).setMovementosRestantesSombreiro(IValor.MOVRESTANTESOMBREIRO);
            }
        }
        return toret+this.getJugadorActual().listarTratos();
    }
    
    public String aumentoPrecioSolares(){
        for(Jugador jugador : listaXogadores){ if(jugador.getAvatar().getNumeroVueltas()<vecesAumentoSolares){ return ""; } }
        
        vecesAumentoSolares+=4;
        for(ArrayList<Casilla> fila : casillas){
            for(Casilla auxiliar : fila){
                if(auxiliar instanceof Solar){ ((Solar)auxiliar).valor(); }
            }
        }
        for(Grupo grupo : listaGrupos){
            if(grupo.getPropiedadesGrupo().get(0) instanceof Solar){
                grupo.setPrecioGrupo(grupo.getPrecioGrupo()*pow(IValor.INFLACION,getVecesAumentoSolares()));
            }
        }
        return "Aumenta o prezo dos Solares.";
    }
    
    public String estadisticas(){
        String nomeCasillaMaisRentable      ="-"; double rentabilidadeDaCasillaMaisRentable=0;
        String nomeCasillaMaisFrecuentada   ="-"; double rentabilidadeGrupoAux=0;
        String nomeGrupoMaisRentable        ="-"; int    vecesPisada=0;
        Jugador jugMaisVoltas; Jugador jugMaisDados; Jugador jugMaisCapital; double maiorCapital;
        
        //CASILLAS E GRUPO
        
        for(Grupo g :listaGrupos){
            if(g.getRentabilidade()>rentabilidadeGrupoAux){
                rentabilidadeGrupoAux = g.getRentabilidade();
                nomeGrupoMaisRentable = g.getNombre();
            }
        }
        for(ArrayList<Casilla> fila : casillas){
            for(Casilla cas : fila){
                if(cas.getRentabilidade()>rentabilidadeDaCasillaMaisRentable){ 
                    nomeCasillaMaisRentable=cas.getNombre(); 
                    rentabilidadeDaCasillaMaisRentable=cas.getRentabilidade();
                }
                if(cas.getVecesPisada()>vecesPisada){ 
                    nomeCasillaMaisFrecuentada=cas.getNombre(); 
                    vecesPisada=cas.getVecesPisada();
                }
            }
        }
        
        //XOGADORES
        jugMaisVoltas= listaXogadores.get(0);
        jugMaisDados= listaXogadores.get(0);
        jugMaisCapital= listaXogadores.get(0);
        maiorCapital=jugMaisCapital.capitalDisponible();
        for(Jugador jug : listaXogadores){
            if((!(jug.equals(jugMaisVoltas))&&(jug.getAvatar().getNumeroVueltas()>jugMaisVoltas.getAvatar().getNumeroVueltas()))) { jugMaisVoltas=jug; }
            if((!(jug.equals(jugMaisDados))&&(jug.getNumTiradasDados()>jugMaisVoltas.getNumTiradasDados()))) { jugMaisDados=jug; }
            if((!(jug.equals(jugMaisCapital))&&(jug.capitalDisponible()>maiorCapital))) { jugMaisCapital=jug; maiorCapital=jug.capitalDisponible(); }
        }
        
        //STRING
        String toret;
        StringBuilder aux=new StringBuilder();
        aux.append("{\n\tCasilla máis rentable: ").append(nomeCasillaMaisRentable).
            append(",\n\tCasilla más frecuentada: ").append(nomeCasillaMaisFrecuentada).
            append(",\n\tGrupo máis rentable: ").append(nomeGrupoMaisRentable).
            append(",\n\tXogador que deu máis voltas: ").append(jugMaisVoltas.getNombre()).
            append(" con un total de: ").append(jugMaisVoltas.getAvatar().getNumeroVueltas()).append(" voltas").
            append(",\n\tXogador que tirou máis veces os dados: ").append(jugMaisDados.getNombre()).append(" con un total de: ").
                append(jugMaisDados.getNumTiradasDados()).append(" veces").
            append(",\n\tXogador con maior capital total(Ferrados e Valor das Propiedades e Edificios): ").append(jugMaisCapital.getNombre()).
                append(", con un total de: ").append(jugMaisCapital.capitalDisponible()).append(" Ferrados").
            append("\n}");
        
        toret=aux.toString();
        return toret;        
    }
    
    //OVERRIDES
    @Override
    public String toString(){
        String toret;
        StringBuilder aux = new StringBuilder();
        
        for(int i=0;i<11;i++){  // Fila 0
            aux.append('|'); aux.append(casillas.get(2).get(i).nombreAColor());              //imprime | e o nome en color
            for(int j=0;j< (IValor.TAMANHOCASILLA) - (casillas.get(2).get(i).getNombre().length()) - (casillas.get(2).get(i).getAvatares().size()) ;j++){ aux.append(" ");}    //imprime os espazos que igualan as casillas                                                                            //imprime espacios necesarios para igualar as casillas
            for(Avatar k : casillas.get(2).get(i).getAvatares()) {aux.append(k.getNombre());}     //imprime avatares se existen na celda
        }aux.append("|\n\n");
        
        for(int j=1;j<10 ;j++){ //Filas 1 á 9                                                         //Imprime a calle Oeste, os espacios e a calle Este
            aux.append('|'); aux.append(casillas.get(1).get(10-j).nombreAColor());
            for(int k=0;k<IValor.TAMANHOCASILLA-casillas.get(1).get(10-j).getNombre().length()-casillas.get(1).get(10-j).getAvatares().size();k++) {aux.append(" ");}
            for(int w=0;w<casillas.get(1).get(10-j).getAvatares().size();w++) {aux.append(casillas.get(1).get(10-j).getAvatares().get(w).getNombre());}
            aux.append("|");
            
            for(int k=0; k < ((IValor.TAMANHOCASILLA+1)*9)-1; k++){ aux.append(' ');}  //tamaño+1 para contar la barra, por 9 casillas vacías, - 1 espacio para la barra de la ultima casilla
                
            aux.append('|'); aux.append(casillas.get(3).get(j).nombreAColor());
            for(int k=0;k<IValor.TAMANHOCASILLA-casillas.get(3).get(j).getNombre().length()-casillas.get(3).get(j).getAvatares().size();k++){aux.append(" ");}
            for(int w=0;w<casillas.get(3).get(j).getAvatares().size();w++){aux.append(casillas.get(3).get(j).getAvatares().get(w).getNombre());}
            aux.append("|\n\n");
        }
        
        for(int h=0;h<11;h++){  //Fila 10
            aux.append('|'); aux.append(casillas.get(0).get(10-h).nombreAColor());
            for(int k=0;k<IValor.TAMANHOCASILLA-casillas.get(0).get(10-h).getNombre().length()-casillas.get(0).get(10-h).getAvatares().size();k++){aux.append(" ");}
            for(int w=0;w<casillas.get(0).get(10-h).getAvatares().size();w++){aux.append(casillas.get(0).get(10-h).getAvatares().get(w).getNombre());}
        }aux.append("|");
        
        toret=aux.toString();
        return toret;
    }
}
