/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO.ejgrafo.grafos;

import controlador.DAO.ejgrafo.CiudadDao;
import controlador.DAO.ejgrafo.RutaDao;
import controlador.ed.grafo.GrafoEtiquetadoD;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.PosicionException;
import controlador.ed.lista.exception.VacioException;
import java.util.HashMap;
import java.util.Map;
import modelo.Ciudad;
import modelo.Ruta;

/**
 *
 * @author apolo
 */
public class CiudadGrafo {

    private GrafoEtiquetadoD<Ciudad> grafo;
    private ListaEnlazada<Ciudad> lista = new ListaEnlazada<>();

    public CiudadGrafo() {
        CiudadDao ciuDao = new CiudadDao();
        lista = ciuDao.listar();
        grafo = new GrafoEtiquetadoD<>(lista.size());

        try {
            for (int i = 0; i < lista.size(); i++) {
                grafo.etiquetarVertice(i + 1, lista.get(i));
                System.out.println(lista.get(i));
                
            }
            System.out.println("<---------------->");
            llenarGrafo();
        } catch (Exception e) {
        }
    }

    public GrafoEtiquetadoD<Ciudad> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEtiquetadoD<Ciudad> grafo) {
        this.grafo = grafo;
    }

    public ListaEnlazada<Ciudad> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Ciudad> lista) {
        this.lista = lista;
    }

    private void llenarGrafo() {
        try {
            for (int i = 0; i < lista.size(); i++) {
                Ciudad ciud = lista.get(i);
                HashMap<String, Double> mapa = new HashMap<>();
                System.out.println("Ciudad (" + ciud.getNombre() + ")");
                ListaEnlazada<Ruta> listaR = new RutaDao().listarPorCiudad(ciud.getId());
                for (int j = 0; j < listaR.size(); j++) {
                    Ruta ruta = listaR.get(j);
                    if (mapa.get(ruta.getNomCiudadDes()) != null) {
                        Double suma = mapa.get(ruta.getNomCiudadDes());
                        suma += ruta.getDistancia();
                        mapa.put(ruta.getNomCiudadDes(), suma);
                    } else {
                        mapa.put(ruta.getNomCiudadDes(), ruta.getDistancia());
                    }
                }
                for (Map.Entry<String, Double> entry : mapa.entrySet()) {
                    
                    Ciudad aux = getPais(entry.getKey());
                    if (aux != null) {
                        System.out.println(aux);
                        grafo.insertarAristaE(ciud, aux, entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error de llenado: " + e);
            e.printStackTrace();
        }
    }

    private Ciudad getPais(String num) throws VacioException, PosicionException {
        Ciudad aux = null;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().equals(num)) {
                aux = lista.get(i);
                break;
            }
        }
        return aux;
    }
}
