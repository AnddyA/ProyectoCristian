/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.modelo;

import controlador.ed.lista.ListaEnlazada;
import javax.swing.table.AbstractTableModel;
import modelo.Ciudad;

/**
 *
 * @author apolo
 */
public class ModeloTablaCiudad extends AbstractTableModel{
    ListaEnlazada<Ciudad> lista = new ListaEnlazada<>();

    public ListaEnlazada<Ciudad> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Ciudad> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    @Override
    public Object getValueAt(int i, int i1) {
        Ciudad ciu = null;
        try {
            ciu = lista.get(i1);
        } catch (Exception e) {
        }
        switch (i1) {
            case 0: return ciu.getId();
            case 1: return ciu.getNombre();
                
            default:
                return null;
        }
    }
    
    public String getColumnName(int column){
        switch (column) {
            case 0: return "ID";
            case 1: return "CIUDAD";
                
            default:
                return null;
        }
    }
    
}
