/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Interfaces.IValor;
import Interfaces.Comando;
import PartesTablero.*;
import Excepciones.*;
import PartesCasilla.*;
import Usuario.*;
import java.util.InputMismatchException;

/**
 *
 * @author eci-toshiba
 */
public final class Juego implements Comando{
    //ATRIBUTOS                                   
    public static ConsolaNormal consola = new ConsolaNormal();  
    public PeticionIncorrectaException comandoIncorrecto;
    private final Tablero tablero;
    
    //PROGRAMA PRINCIPAL
    public Juego(){
        this.comandoIncorrecto  = new PeticionIncorrectaException("Ese comando non está soportado."); 
        this.tablero            = new Tablero();
        
        
        iniciarJugadores();
            
        listarComandos();
        consola.imprimir(tablero.toString()+"\nTurno de: " + tablero.getJugadores().get(tablero.getTurnoJugador()).getNombre()+".\nModo de movemento actual: Normal.");
        do{
            try{
                while(tablero.getJugadores().size()>1 && tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getNumTurnosCarcel()==4){
                    consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).pagoSalirCarcel());
                }
                if(tablero.getJugadores().size()>1){
                    consola.imprimir(tablero.aumentoPrecioSolares());

                    String orden; 
                    String[] partes;

                    orden =consola.leer("Introduce o comando: ");
                    partes=orden.split(" ");

                    switch(partes[0].toLowerCase()){
                        case "rendirte":
                        case "rendirme":
                        case "rendirse":{
                            if(partes.length==1){ rendirse(); break; }   
                            throw comandoIncorrecto;
                        }
                        case "voltas":{
                            if(partes.length==1){ voltas(); break; }
                            throw comandoIncorrecto;
                        }
                        case "xogador":{
                            if(partes.length==1){ xogador(); break; }
                            throw comandoIncorrecto;
                        }
                        case "hipotecar":{
                            if(partes.length>1) { hipotecarPropiedade(); break; }
                            throw comandoIncorrecto;
                        }
                        case "deshipotecar":{
                            if(partes.length>1) { deshipotecarPropiedade(); break; }
                            throw comandoIncorrecto;
                        }   
                        case "informar":
                        case "informacion":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("edificios")) { informacionEdificios(); break; }
                            else if(partes.length==2 && partes[1].equalsIgnoreCase("tratos")) { informacionTratos(); break; }
                            else if(partes.length==3 && partes[1].equalsIgnoreCase("movemento")&& partes[2].equalsIgnoreCase("avanzado")) { informacionMovementoAvanzado(); break; }
                            throw comandoIncorrecto;
                        }
                        case "cambiar":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("modo"))      { cambiarModo(); break; }
                            throw comandoIncorrecto;
                        }
                        case "lanzar":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("dados") )    { lanzarDados(); break; }
                            throw comandoIncorrecto;
                        }
                        case "terminar":
                        case "acabar":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("turno") )    { acabarTurno(); break; }
                            throw comandoIncorrecto;
                        }
                        case "ver":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("taboleiro") ){ verTaboleiro(); break; }
                            throw comandoIncorrecto;
                        }
                        case "sair":{
                            if(partes.length==2 && partes[1].equalsIgnoreCase("carcere") )  { sairCarcere(); break; }    
                            throw comandoIncorrecto;
                        }
                        case "estatisticas":{
                            if(partes.length==1)        { estadisticas(); break; }
                            else if(partes.length==2)   { estadisticasXogador(partes[2]); break; }
                            throw comandoIncorrecto;
                        }
                        case "listar":{
                            if(partes.length==2){
                                switch(partes[1].toLowerCase()){
                                    case "comandos":{ listarComandos(); break; }
                                    case "xogadores":{listarXogadores();break; }
                                    case "avatares":{ listarAvatares(); break; }
                                    case "enventa": { listarEnVenta();  break; }
                                    case "edificios":{listarEdificios();break; }
                                    case "tratos":  { listarTratos();   break; }
                                    default:        { throw comandoIncorrecto; }
                                }
                                break;
                            }
                            else if(partes.length==3){
                                switch(partes[1].toLowerCase()){
                                    case "edificios":{ listarEdificiosGrupo(partes[2]); break; }
                                    case "tratos":{
                                        if(partes[2].equalsIgnoreCase("propostos")){ listarTratosPropuestos(); break; }
                                        throw comandoIncorrecto;
                                    } 
                                    default:{ throw comandoIncorrecto; }
                                }
                                break;
                            }
                            throw comandoIncorrecto;
                        }
                        case "trato":{
                            if(partes.length>4 && partes[2].equalsIgnoreCase("cambiar")){
                                String aux="";
                                for(int i=3;i<partes.length;i++){
                                    aux+=partes[i];
                                    if(i!=partes.length-1){
                                        aux+=" ";
                                    }
                                }
                                proponerTrato(partes[1],aux);
                                break;
                            }
                            throw comandoIncorrecto;
                        }
                        case "aceptar":{
                            if(partes.length==2){ aceptarTrato(partes[1]); break; }
                            throw comandoIncorrecto;
                        }
                        case "eliminar":{
                            if(partes.length==2){ eliminarTrato(partes[1]); break; }
                            throw comandoIncorrecto;
                        }
                        case "definir":
                        case "describir":{
                            if(partes.length>=2){
                                switch(partes[1].toLowerCase()){
                                    case "xogador":{
                                        if(partes.length==3){ describirXogador(partes[2].toLowerCase()); break; }
                                        throw comandoIncorrecto;
                                    }    
                                    case "avatar":{
                                        if(partes.length==3){ describirAvatar(partes[2]); break; }
                                        throw comandoIncorrecto;
                                    }       
                                    default:{
                                        String aux="";
                                        for(int i=1;i<partes.length;i++){
                                            aux+=partes[i];
                                            if(i!=partes.length-1){
                                                aux+=" ";
                                            }
                                        }
                                        describirCasilla(aux.toLowerCase());
                                        break;
                                    }    
                                }
                                break;
                            }
                            throw comandoIncorrecto;
                        }
                        case "construir":
                        case "edificar":{
                            String aux="";
                                for(int i=1;i<partes.length;i++){
                                    aux+=partes[i];
                                    if(i!=partes.length-1){
                                        aux+=" ";
                                    }
                                }
                            if(partes.length>=2&&partes.length<=3){
                                construirEdificio(aux.toLowerCase()); 
                                break;
                            }
                            throw new PeticionIncorrectaException("O tipo de edificio: "+IValor.BROJO+ aux+IValor.RESET+" non existe.");
                        }
                        case "vender":{
                            if(partes.length>=4){ venderEdificio(orden.toLowerCase()); break; }
                            throw comandoIncorrecto; 
                        }
                        case "comprar":{
                            if(partes.length>1){
                                String aux="";
                                for(int i=1;i<partes.length;i++){
                                    aux+=partes[i];
                                    if(i!=partes.length-1){
                                        aux+=" ";
                                    }
                                }
                                comprarPropiedade(aux.toLowerCase());
                                break;
                            }
                            throw comandoIncorrecto; 
                        }
                        default:{ throw comandoIncorrecto; }
                    }
                }
            }catch(NullPointerException datoNull){
                consola.imprimir(IValor.BROJO+"Atopouse un erro inesperado. Por favor, inténtao de novo. "+IValor.RESET);
            }catch(InputMismatchException | NumberFormatException  tipoIncorrecto){
                consola.imprimir(IValor.BROJO+"Introduciuse un tipo de dato erróneo. Por favor, introduce un dato do tipo requerido. "+IValor.RESET);
            }catch(PeticionIncorrectaException noComando){
                consola.imprimir(noComando.getMessage());
            }
        }while(tablero.getJugadores().size()>1);
        consola.imprimir(tablero.getJugadores().get(0).getNombre() + " GAÑA!!!");
    }
    
    //METODOS
    

    
    //SUBMENU INICIAR XOGADORES
    public void iniciarJugadores(){                                 
        String nombre="NOMEERRO";
        String tipoAvatar="";
        boolean iniciar=false;
        do{
            try{
                boolean bucles;
                switch(consola.leer("Lista de comandos: \n -Crear Xogador\n -Iniciar Partida\nIntroduce o comando: ").toLowerCase()){
                    case "crear xogador":{                                                 
                        if(tablero.getJugadores().size()<6){
                            do{
                                bucles=false;
                                try{
                                    nombre=consola.leer("Introduce o nome do xogador:").replaceAll(" ","");
                                    for (Jugador aux : tablero.getJugadores()) {if(aux.getNombre().equalsIgnoreCase(nombre)){ throw new DatoIncorrectoException();}}
                                }catch(DatoIncorrectoException nomeEnUso){consola.imprimir("Ese nome xa está en uso."); bucles=true;}        
                            }while(bucles);
                            do{
                                tipoAvatar=consola.leer("Introduce o tipo de Avatar (Pelota, Coche, Esfinxe ou Sombreiro):").toLowerCase();
                                if(tipoAvatar.equals("pelota") ||tipoAvatar.equals("coche") ||tipoAvatar.equals("esfinxe") ||tipoAvatar.equals("sombreiro")){
                                    bucles=true;
                                    continue;
                                }
                                consola.imprimir("Tipo de Avatar Incorrecto");
                            }while(!bucles);
                            switch(tipoAvatar.toLowerCase()){
                                case "pelota":      { tablero.getJugadores().add(new Pelota(tablero, nombre).getJugador()); break; }
                                case "coche":       { tablero.getJugadores().add(new Coche(tablero, nombre).getJugador()); break; }
                                case "esfinxe":     { tablero.getJugadores().add(new Esfinge(tablero, nombre).getJugador()); break; }
                                case "sombreiro":   {tablero.getJugadores().add(new Sombrero(tablero, nombre).getJugador()); break; }
                            }
                            tablero.getJugadores().forEach((i) -> {consola.imprimir(i.toString());});
                            break;
                        }
                        throw new PeticionIncorrectaException("Número máximo de xogadores alcanzado (6).");
                    }
                    case "iniciar partida":{
                        if(tablero.getJugadores().size()>1){ iniciar=true; break; }
                        throw new PeticionIncorrectaException("Mínimo 2 xogadores.");
                    }
                    default:{ throw new PeticionIncorrectaException("Ese comando non está soportado."); }
                }
            }catch(PeticionIncorrectaException comandoInc){
                consola.imprimir(comandoInc.getMessage());
            }
        }while(!iniciar);
    }
    
    //OVERRIDES
    @Override
    public void venderEdificio(String orde) throws PeticionIncorrectaException, NumberFormatException{
        String[] partes=orde.split(" ");
        if(partes[1].equals("casa")||partes[1].equals("hotel")||partes[1].equals("piscina")||(partes[1].equals("pista")&& partes[2].equals("deportiva"))){
            if(partes.length>=5&&partes[1].equals("pista")){
                String[] reducionString = new String[partes.length-1];
                reducionString[0] = partes[0]; reducionString[1] = partes[1]+partes[2];
                for(int i=2;i<partes.length-1;i++){ reducionString[i] = partes[i+1]; }
                partes = reducionString;
            }
            String aux="";
            for(int i=2;i<partes.length-1;i++){
                aux+=partes[i];
                if(i!=partes.length-2){ aux+=" "; }
            }
            if(tablero.buscarCasilla(aux) instanceof Solar){
                    int numEdificios=Integer.parseInt(partes[partes.length-1]);
                    consola.imprimir(((Solar)tablero.buscarCasilla(aux)).venderEdificios(partes[1], numEdificios));
                }
            else{ throw new NonTipoCasillaCorrectoException(tablero.buscarCasilla(aux).nombreAColor());} 
        }
        else throw new DatoIncorrectoException("O tipo de edificio: "+IValor.BROJO+partes[1]+IValor.RESET+" non existe.");
    }
    
    @Override
    public void describirXogador(String nomeXogador) throws PeticionIncorrectaException{
        boolean atopado=false;
        for(Jugador jugador : tablero.getJugadores()){
            if(jugador.getNombre().equalsIgnoreCase(nomeXogador)){
                consola.imprimir(jugador.toString());
                atopado=true; break;
            }
        }
        if(!atopado){ throw new DatoIncorrectoException("O xogador: "+IValor.BROJO+nomeXogador+IValor.RESET+" non foi atopado."); } 
    }
    
    @Override
    public void describirAvatar(String nomeAvatar)throws DatoIncorrectoException{
        boolean atopado=false;
        for(Jugador jugador : tablero.getJugadores()){
            if(jugador.getAvatar().getNombre().equals(nomeAvatar)){ 
                consola.imprimir(jugador.getAvatar().toString());
                atopado=true; break;
            }
        }
        if(!atopado){ throw new DatoIncorrectoException("O avatar: "+IValor.BROJO+nomeAvatar+IValor.RESET+" non foi atopado."); }
    }
    
    @Override
    public void describirCasilla(String nomeCasilla) throws DatoIncorrectoException{
            consola.imprimir(tablero.buscarCasilla(nomeCasilla).toString());
    }
    
    @Override
    public void comprarPropiedade(String nomeCasilla)  throws PeticionIncorrectaException{
        if(tablero.buscarCasilla(nomeCasilla) instanceof Propiedad){
            consola.imprimir(((Propiedad) tablero.buscarCasilla(nomeCasilla)).comprar());
        }
    }
    
    @Override
    public void construirEdificio(String tipoEdificio) throws PeticionIncorrectaException{   
        if(tipoEdificio.equalsIgnoreCase("casa")||tipoEdificio.equalsIgnoreCase("hotel")||tipoEdificio.equalsIgnoreCase("piscina")||(tipoEdificio.equals("pista deportiva"))){
            if(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getCasillaActual() instanceof Solar){
                consola.imprimir(((Solar)tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getCasillaActual()).edificar(tipoEdificio));
            }
            else{ throw new NonTipoCasillaCorrectoException(tablero.getJugadorActual().getAvatar().getCasillaActual().nombreAColor()); }
        }
    }
    
    @Override
    public void listarEdificiosGrupo(String nomeGrupo)throws PeticionIncorrectaException{
        boolean atopado=false;
        
        if(nomeGrupo.equals("transportes")||nomeGrupo.equals("servizos")){ throw new CondicionNonCumplidaException("O grupo: "+IValor.BROJO+nomeGrupo+IValor.RESET+" non pode ser edificado."); }
        else{
            for(Grupo g:tablero.getGrupos()){
                if(g.getNombre().toLowerCase().equals(nomeGrupo)){
                    atopado=true;
                    consola.imprimir(g.listarEdificiosGrupo());
                    break;
                }
            }
        }
        if(!atopado){ throw new DatoIncorrectoException("O grupo: "+IValor.BROJO+nomeGrupo+IValor.RESET+" non existe."); }
    }
    
    @Override
    public void listarEdificios()throws DatoIncorrectoException{
        if(tablero.getEdificios().isEmpty()){ throw new DatoIncorrectoException("Non hai ningún edificio construido."); }
        tablero.getEdificios().forEach((i) -> {consola.imprimir(i.toString());}); 
    }
    
    @Override
    public void listarComandos(){
        String toret;
        StringBuilder aux = new StringBuilder();
        aux.append("\n");
        aux.append("Lista de comandos:\n\n");
       
        aux.append("-Vender <TipoEdificio> <NomeSolar> <CantidadeAVender>\n");
        aux.append("-Describir Xogador <NomeXogador>\n");
        aux.append("-Describir Avatar <NomeAvatar>\n");
        aux.append("-Describir <NomeCasilla>\n");
        aux.append("-Comprar <NomeCasilla>\n");
        aux.append("-Construir <TipoEdificio>\n");
        aux.append("-Listar Edificios <NomeGrupo>\n");
        aux.append("-Listar Edificios\n");
        aux.append("-Listar Comandos\n");
        aux.append("-Listar Xogadores\n");
        aux.append("-Listar Avatares\n");
        aux.append("-Listar EnVenta\n");
        aux.append("-Listar Tratos\n");
        aux.append("-Listar Tratos Propostos\n");
        aux.append("-Trato <NomeXogador> Cambiar <Ofreces> por <Pides>\n");
        aux.append("-Informacion Tratos\n");
        aux.append("-Aceptar <NomeTrato>\n");
        aux.append("-Eliminar <NomeTrato>\n");
        aux.append("-Ver Taboleiro\n");
        aux.append("-Cambiar Modo\n");
        aux.append("-Lanzar Dados\n");
        aux.append("-Sair Carcere\n");
        aux.append("-Acabar Turno\n");
        aux.append("-Informacion Edificios\n");
        aux.append("-Estatisticas <NomeXogador>\n");
        aux.append("-Estatisticas\n");
        aux.append("-Hipotecar \n");
        aux.append("-Deshipotecar \n");
        aux.append("-Voltas\n");
        aux.append("-Xogador\n");
        aux.append("-Rendirse\n");
          
        toret=aux.toString();
        consola.imprimir(toret);
    }
    
    @Override
    public void listarXogadores(){
        tablero.getJugadores().forEach((i) -> {consola.imprimir(i.toString());});
        consola.imprimir(tablero.toString());
    }
    
    @Override
    public void listarAvatares(){
        tablero.getJugadores().forEach((i) -> {consola.imprimir(i.getAvatar().toString());});
        consola.imprimir(tablero.toString());
    }
    
    @Override
    public void listarEnVenta(){
        tablero.getGrupos().forEach((i) -> {consola.imprimir(i.toString());});
    }
    
    @Override
    public void verTaboleiro(){
        consola.imprimir(tablero.toString());
    }
    
    @Override
    public void cambiarModo(){
        consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().cambiarModo());
    }
    
    @Override
    public void lanzarDados() throws PeticionIncorrectaException{
        consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).lanzarDados());
    }
    
    @Override
    public void sairCarcere() throws PeticionIncorrectaException{
        if(!tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().isEncarcelado()){throw new CondicionNonCumplidaException(" saír do Cárcere se non estás encarcelado!!!");}
        if(tablero.isIntento()){throw new CondicionNonCumplidaException(" intentar saír do Cárcere dúas veces no mesmo turno.");}
        if(tablero.getJugadores().get(tablero.getTurnoJugador()).getDineroActual()<=IValor.VALORSALIRCARCEL){throw new CondicionNonCumplidaException(" costearte saír do Cárcere!");}
        
        consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).pagoSalirCarcel());
    }
    
    @Override
    public void acabarTurno()throws PeticionIncorrectaException{
        if(!tablero.isPodeLanzarDados()||
           (tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar() instanceof Coche && ((Coche)tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar()).getMovementosRestantesCoche()<IValor.MOVRESTANTECOCHE) || 
           (tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar() instanceof Esfinge && ((Esfinge)tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar()).getMovementosRestantesEsfinxe()<IValor.MOVRESTANTEESFINXE) ||
                (tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar() instanceof Sombrero && ((Sombrero)tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar()).getMovementosRestantesSombreiro()<IValor.MOVRESTANTESOMBREIRO)){
                consola.imprimir(tablero.resetTurno(false)); 
        } else{throw new CondicionNonCumplidaException(" acabar o turno sen moverte!!");}
    }
        
    @Override
    public void informacionEdificios(){
        String toret;
        StringBuilder aux = new StringBuilder();
        
        aux.append("\n");
        aux.append("Os edificios so se poden construir nas casillas de tipo solar.\n");
        aux.append("Para construir un edificio é necesario estar no solar no que se quere construir.\n");
        aux.append("Para poder construir un edificio é necesario posuir todas as casillas do grupo ao que pertence ou ter caido dúas veces no solar.\n");
        aux.append("O número máximo de edificios de cada tipo por grupo é igual ao tamaño do grupo dese solar.\n");
        
        aux.append("\nTipos de edificio:\n\n");
        
        
        aux.append(" -Casa: Pódese superar o número máximo de casas por solar (ate 4) sempre que non se teña o número máximo de hoteis nese solar.\n");
        aux.append(" -Hotel: Para construir un hotel é necesario ter 4 casas construidas nese mesmo solar. Construir o hotel eliminará as casas.\n");
        aux.append(" -Piscina: Para construir unha piscina é necesario ter 2 casas e un hotel construidos nese mesmo grupo.\n");
        aux.append(" -Pista Deportiva: Para construir unha pista de deporte é necesario ter 2 hoteis construidos nese mesmo grupo.\n");
        
        aux.append("\nPrecios:\n");
        
        aux.append(" -Casa: Prezo do solar * ").append(IValor.FACTORCASA).append(".\n");
        aux.append(" -Hotel: Prezo do solar * ").append(IValor.FACTORHOTEL).append(".\n");
        aux.append(" -Piscina: Prezo do solar * ").append(IValor.FACTORPISCINA).append(".\n");
        aux.append(" -Pista Deportiva: Prezo do solar * ").append(IValor.FACTORPISTADEPORTIVA).append(".\n");
        
        toret=aux.toString();
        consola.imprimir(toret);
    }
    
    @Override
    public void estadisticasXogador(String nomeXogador)throws PeticionIncorrectaException{
        boolean atopado=false;
        for(Jugador jugador : tablero.getJugadores()){
            if(jugador.getNombre().equalsIgnoreCase(nomeXogador)){
                consola.imprimir(jugador.estadisticas());
                atopado=true; break;
            }
        }
        if(!atopado){ throw new DatoIncorrectoException("Non se pode atopar o xogador: "+IValor.BROJO+nomeXogador+IValor.RESET+"."); }
    }
    
    @Override
    public void estadisticas(){ consola.imprimir(tablero.estadisticas()); }
    
    @Override
    public void hipotecarPropiedade() throws PeticionIncorrectaException{ 
        if(tablero.getJugadores().get(tablero.getTurnoJugador()).getPropiedades().size()>tablero.getJugadores().get(tablero.getTurnoJugador()).getHipotecas().size()){
            boolean nombreCorrecto=false;
            tablero.getJugadores().get(tablero.getTurnoJugador()).getPropiedades().forEach((p)->{ if( !p.isHipotecada() ){consola.imprimir(p.toString());}});
            String comando = consola.leer("\nQue Propiedade queres hipotecar?");
            for(Propiedad p : tablero.getJugadores().get(tablero.getTurnoJugador()).getPropiedades()){
                if(!p.isHipotecada() && p.getNombre().equalsIgnoreCase(comando) ){
                    if(!(p instanceof Solar) || ((Solar) p).getEdificios().isEmpty()){
                        p.hipotecar(); nombreCorrecto=true;
                        break;
                    }
                    throw new DatoIncorrectoException("Para hipotecar necesitas primeiro vender todos os edificios da propiedade.");
                } 
            }
            if(!nombreCorrecto){throw new NonPosuidoException(comando);}
        }    
        else{throw new DatoIncorrectoException("Non tes propiedades hipotecables.");}
    }
    
    @Override
    public void deshipotecarPropiedade() throws PeticionIncorrectaException{
        if(tablero.getJugadores().get(tablero.getTurnoJugador()).getHipotecas().size()>0){
            boolean nombreCorrecto=false;
            tablero.getJugadores().get(tablero.getTurnoJugador()).getHipotecas().forEach((p)->{consola.imprimir(p.toString()); });
            String comando = consola.leer("\nQue Propiedade queres deshipotecar?");
            for(Propiedad h : tablero.getJugadores().get(tablero.getTurnoJugador()).getHipotecas()){
                if(h.getNombre().toLowerCase().equals(comando)){
                    if(tablero.getJugadores().get(tablero.getTurnoJugador()).getDineroActual()>h.getValorHipoteca()*1.1){
                        h.deshipotecar(); nombreCorrecto=true;
                        break;
                    }
                    throw new NonFerradosException("deshipotecar "+comando);
                } 
            }
            if(!nombreCorrecto){throw new NonPosuidoException(comando);}
        }
        else{throw new DatoIncorrectoException("Non tes propiedades hipotecadas.");}
    }
    
    @Override
    public void voltas(){
        for(Jugador jugador : tablero.getJugadores()){
            if(jugador.getAvatar().getNumeroVueltas()<tablero.getVecesAumentoSolares()){
                consola.imprimir("A "+jugador.getNombre()+" fáltanlle dar "+(tablero.getVecesAumentoSolares()-jugador.getAvatar().getNumeroVueltas())+" voltas antes de que aumente o prezo dos solares.");
            }
        }
    }
    
    @Override
    public void xogador(){           
        String toret;
        StringBuilder aux=new StringBuilder();
        aux.append("{\nNome: ").append(tablero.getJugadores().get(tablero.getTurnoJugador()).getNombre()).
            append(",\nAvatar: ").append(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getNombre()).
            append(",\nTipo de Avatar: ").append(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getClass().getSimpleName()).
            append(",\nPode moverse: "); 
        if(tablero.isPodeLanzarDados()) { aux.append("Si."); } else { aux.append("Non."); }
        aux.append("\nModo de movemento actual: ");
        if(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().isModoAvanzado()){aux.append("Avanzado.");}
        else {aux.append("Normal.");}
        if(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().isEncarcelado()){
            aux.append("\nTurnos encarcelado: ").append(tablero.getJugadores().get(tablero.getTurnoJugador()).getAvatar().getNumTurnosCarcel());
        }
        
        aux.append("\n}");
        toret=aux.toString();
        consola.imprimir(toret);
    }
    
    @Override
    public void rendirse() throws DatoIncorrectoException{
        String orden=consola.leer("\nEstás seguro de que queres rendirte?\nSi/Non.");
        if(orden.equalsIgnoreCase("si")){
            consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).getNombre()+" réndese!!");
            consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).bancarrota(tablero.getLaBanca(), 0));
        }
    }

    public static void submenu(double aPagar, Jugador jugador){
        do {
            try{
                consola.imprimir("\nAínda necesitas " + (aPagar - jugador.getDineroActual()) + " ferrados, hipoteca algunha propiedade ou vende algún edificio.");
                jugador.getPropiedades().forEach((Propiedad p) -> {
                    if (!p.isHipotecada()) {
                        consola.imprimir(p.toString());
                    }
                });
                consola.imprimir(jugador.toString());
                String orde;
                String[] partes;
                do {
                    orde = consola.leer("\nComandos:\n\t-Hipotecar\n\t-Vender <TipoEdificio> <NomeCasilla> <CantidadeAVender>").toLowerCase();
                    partes = orde.split(" ");
                } while (!partes[0].equalsIgnoreCase("hipotecar") && !partes[0].equalsIgnoreCase("vender"));
                
                if (partes[0].equalsIgnoreCase("hipotecar") && partes.length==1){
                    String comando = consola.leer("\nQue Propiedade queres hipotecar?");
                    boolean nombreCorrecto = false;
                    for (Propiedad p : jugador.getPropiedades()) {
                        if (!p.isHipotecada() && p.getNombre().equalsIgnoreCase(comando)) {
                            if (!(p instanceof Solar) || ((Solar) p).getEdificios().isEmpty()) { p.hipotecar(); nombreCorrecto=true; break; }
                            throw new DatoIncorrectoException("\nPara hipotecar necesitas primeiro vender todos os edificios da propiedade.");
                        }
                    }
                    if (!nombreCorrecto) { throw new DatoIncorrectoException("\nNingunha das tuas propiedades hipotecables ten ese nome."); }
                    
                } else {
                    if (partes.length >= 4) {
                        if (partes.length >= 5 && partes[1].equals("pista")) {
                            String[] reducionString = new String[partes.length - 1];
                            reducionString[0] = partes[0]; reducionString[1] = partes[1] + partes[2];
                            for (int i = 2; i < partes.length - 1; i++) { reducionString[i] = partes[i + 1]; }
                            partes = reducionString;
                        }
                        String aux = "";
                        for (int i = 2; i < partes.length - 1; i++) {
                            aux += partes[i];
                            if (i != partes.length - 2) { aux += " "; }
                        }
                        if (jugador.getAvatar().getTablero().buscarCasilla(aux) instanceof Solar) {
                            try{
                                int numEdificios = Integer.parseInt(partes[partes.length - 1]);
                                consola.imprimir(((Solar) jugador.getAvatar().getTablero().buscarCasilla(aux)).venderEdificios(partes[1], numEdificios));
                            }catch(NumberFormatException parseIntNoNumException){ consola.imprimir("Introduciches un número erróneo: "+partes[partes.length - 1]); }
                        } else { throw new NonTipoCasillaCorrectoException("Solo podes edificar nos Solares."); }
                    } else { throw new DatoIncorrectoException("O tipo de edificio: "+IValor.BROJO+partes[1]+IValor.RESET+" non existe."); }
                }
            }catch(PeticionIncorrectaException noComando){ consola.imprimir(noComando.getMessage()); }
        } while (jugador.getDineroActual() < aPagar);
    }
    
    @Override
    public void proponerTrato(String nombre, String condiciones)throws PeticionIncorrectaException{
        boolean atopado=false;
        for (Jugador jug : tablero.getJugadores()) {
            if (jug.getNombre().equalsIgnoreCase(nombre)) {
                atopado=true;
                if (!jug.equals(tablero.getJugadores().get(tablero.getTurnoJugador()))) {
                    String[] partes; String[] ofrecer;
                    String[] pedir;  String[] subPartes;
                    Casilla auxOfrecer=null; Casilla auxPedir=null;
                    Casilla auxNoPagar=null;
                    double auxDinero=0; int numTurnos=0;
                    
                    partes=condiciones.split(" por ");
                    if(partes.length!=2){throw new PeticionIncorrectaException("Comando incorrecto.");}
                    
                    ofrecer=partes[0].split(" e "); pedir=partes[1].split(" e ");
                    switch (ofrecer.length) {
                        case 1:
                            try {
                                auxDinero=Double.parseDouble(ofrecer[0]);
                                if (auxDinero<=0) { throw new DatoIncorrectoException("Número introducido incorrecto."); }
                            }catch(NumberFormatException | InputMismatchException noNumero){
                                auxOfrecer=tablero.buscarCasilla(ofrecer[0]);
                            } catch (NullPointerException nonAtopado) {
                                consola.imprimir(ofrecer[0]+nonAtopado);////////////////
                            }
                            break;
                        case 2:
                            try {
                                auxDinero=Double.parseDouble(ofrecer[1]);
                                if (auxDinero<=0) { throw new DatoIncorrectoException("Número incorrecto: "+auxDinero+"."); }
                                auxOfrecer=tablero.buscarCasilla(ofrecer[0]);
                            } catch (NumberFormatException | InputMismatchException noNumero) {
                                throw new DatoIncorrectoException("Número introducido incorrecto.");
                            } catch (NullPointerException  nonAtopado) {
                                consola.imprimir(ofrecer[0]+nonAtopado);
                            }
                            break;
                        default: {throw new DatoIncorrectoException("Comando incorrecto.");}
                    }
                    switch (pedir.length) {
                        case 1:
                            if (auxDinero!=0) {
                                try {
                                    auxPedir=tablero.buscarCasilla(pedir[0]);
                                    consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).proponerTrato(jug,auxOfrecer,auxPedir,auxDinero,auxNoPagar,numTurnos));
                                } catch (DatoIncorrectoException | NullPointerException nonAtopado) {
                                    consola.imprimir(pedir[0]+nonAtopado);
                                    break ;
                                }
                            } else {
                                try {
                                    auxDinero=Double.parseDouble(pedir[0])*(-1);
                                    if (auxDinero>=0) { throw new DatoIncorrectoException("Número incorrecto: "+auxDinero+"."); }
                                    
                                    consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).proponerTrato(jug,auxOfrecer,auxPedir,auxDinero,auxNoPagar,numTurnos));
                                }catch(NumberFormatException | InputMismatchException noNumero){
                                    auxPedir=tablero.buscarCasilla(pedir[0]);
                                    consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).proponerTrato(jug,auxOfrecer,auxPedir,auxDinero,auxNoPagar,numTurnos));
                                } catch (NullPointerException nonAtopado) {
                                    consola.imprimir(pedir[0]+nonAtopado);
                                    break ;
                                }
                            }
                            break;
                        case 2:
                            if (auxDinero!=0||auxOfrecer==null) {throw new PeticionIncorrectaException("Comando incorrecto.");}
                            try {
                                auxPedir=tablero.buscarCasilla(pedir[0]);
                                auxDinero=Double.parseDouble(pedir[1])*(-1);
                                consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).proponerTrato(jug,auxOfrecer,auxPedir,auxDinero,auxNoPagar,numTurnos));
                            } catch (NumberFormatException | InputMismatchException noNumero) {
                                subPartes=pedir[1].split(" ");
                                if (subPartes.length<3||!subPartes[0].equalsIgnoreCase("nonpagar")) {
                                    throw new PeticionIncorrectaException("Comando incorrecto.");
                                }
                                String aux="";
                                for(int i=1;i<subPartes.length-1;i++){
                                    aux+=subPartes[i];
                                    if(i!=subPartes.length-2){
                                        aux+=" ";
                                    }
                                }   auxNoPagar=tablero.buscarCasilla(aux);
                                numTurnos=Integer.parseInt(subPartes[subPartes.length-1]);
                                
                                if (numTurnos<1) {throw new PeticionIncorrectaException("Comando incorrecto."); }
                                consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).proponerTrato(jug,auxOfrecer,auxPedir,auxDinero,auxNoPagar,numTurnos));
                            } catch (PeticionIncorrectaException | NullPointerException nonAtopado) {
                                consola.imprimir(ofrecer[0]+nonAtopado);
                                break;
                            }
                            break;
                        default:{throw new PeticionIncorrectaException("Comando incorrecto.");}
                    }
                    break;
                }
                throw new CondicionNonCumplidaException(" propoñerte un trato a ti mesmo.");
            }
        }
        if(!atopado){throw new DatoIncorrectoException("O xogador non puido ser atopado");}
    }
    
    @Override    
    public void aceptarTrato(String nomeTrato)throws PeticionIncorrectaException{
        boolean encontrado=false;
        for(Trato t: tablero.getJugadores().get(tablero.getTurnoJugador()).getTratosPendientes()){
            if(t.getIdTrato().equalsIgnoreCase(nomeTrato)){
                consola.imprimir(t.aceptarTrato());
                encontrado=true;
                break;
            }
        }
        if(!encontrado){throw new DatoIncorrectoException("Non tes ningun trato pendiente con ese nome.");}
    }
    
    @Override
    public void eliminarTrato(String nomeTrato)throws PeticionIncorrectaException{
        boolean encontrado=false;
        for(Trato t: tablero.getJugadores().get(tablero.getTurnoJugador()).getTratosPropuestos()){
            if(t.getIdTrato().equalsIgnoreCase(nomeTrato)){
                t.eliminarTrato();
                consola.imprimir("Trato eliminado");
                encontrado=true;
                break;
            }
        }
        if(!encontrado){throw new DatoIncorrectoException("Non tes ningun trato proposto con ese nome.");}
        
    }
    
    @Override
    public void listarTratos(){
        consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).listarTratos());
    }
    
    @Override
    public void listarTratosPropuestos(){
        consola.imprimir(tablero.getJugadores().get(tablero.getTurnoJugador()).listarTratosPropuestos());
    }
    
    @Override
    public void informacionTratos(){
        String toret;
        StringBuilder aux = new StringBuilder();
        
        aux.append("\n");
        aux.append("Sintaxe dos tipos de trato:\n\n");
        
        aux.append(" Trato <NomeXogador> Cambiar <PropiedadeOfrecida> por <PropiedadePedida>\n");
        aux.append(" Trato <NomeXogador> Cambiar <PropiedadeOfrecida> por <CantidadeFerrados>\n");
        aux.append(" Trato <NomeXogador> Cambiar <CantidadeFerrados> por <PropiedadePedida>\n");
        aux.append(" Trato <NomeXogador> Cambiar <PropiedadeOfrecida> e <CantidadeFerrados> por <PropiedadePedida>\n");
        aux.append(" Trato <NomeXogador> Cambiar <PropiedadeOfrecida> por <PropiedadePedida> e <CantidadeFerrados>\n");
        aux.append(" Trato <NomeXogador> Cambiar <PropiedadeOfrecida> por <PropiedadePedida> e nonpagar <PropiedadeNonPagar> <TurnosNonPagar>\n");
        
        
        toret=aux.toString();
        consola.imprimir(toret);
    }
    
    @Override
    public void informacionMovementoAvanzado(){
        String toret;
        StringBuilder aux = new StringBuilder();
        
        aux.append("{\n");
        aux.append("O movemento avanzado dos avatares divídese en dous tipos:\n\n");
        
        aux.append(" -Sacando un 4 ou menos na tirada de dados, recívese un efecto negativo, sendo estes:\n").
            append("\tCoche: Durante os 2 próximos turnos non poderá lanzar dados.\n").
            append("\tPelota: Moverase cara atrás, parando nas casillas impares(dende a orixe) e non podendo comprar as propiedades pero si aplicándose outras resolucións.\n").
            append("\tEsfinxe e Sombreiro: 'Desfaise' o movemento anterior, pagándolle á Banca a suma de todos os Ferrados gañados e recivindo da mesma todos os perdidos no turno anterior."
                + " Todas as accións como comprar ou edificar serán desfeitas.\n").
            append("\n -Sacando máis dun 4, actívase o movemento avanzado de cada avatar, sendo estes:\n").
            append("\tCoche: O xogador poderá lanzar os dados ata 3 veces adicionais, podendo activarse o efecto negativo en calquera das 4 lanzadas.\n").
            append("\tPelota: O avatar parará en cada casilla impar dende a orixe ata chegar ao destino, podendo comprar sen límite se cae nunha propiedade en venta.\n").
            append("\tEsfinxe: O avatar moverase en zigzag de Norte a Sur, saltándose unha casilla de cada rúa e podendo comprar como a Pelota.\n").
            append("\tSombreiro: O avatar moverase en zigzag de Leste a Oeste, saltándose unha casilla de cada rúa e podendo comprar como a Pelota.\n");
        
        aux.append("}\n");
        
        toret=aux.toString();
        consola.imprimir(toret);
    }
}
