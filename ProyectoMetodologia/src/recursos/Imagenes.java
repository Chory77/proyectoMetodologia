/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author rodri
 */
public class Imagenes {
    
    public Image cargar (String sRuta){
        
        return Toolkit.getDefaultToolkit().createImage((getClass().getResource(sRuta)));
        
    }
}
