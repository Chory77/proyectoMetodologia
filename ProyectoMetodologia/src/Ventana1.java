
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import recursos.Imagenes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rodri
 */
public class Ventana1 extends javax.swing.JFrame implements MouseListener {

    Image portada;
    Image tablero;
    int nEstado = 0;

    int Tablero1[][] = new int[8][8];
    int Tablero2[][] = new int[8][8];

    int pFila = 0;
    int pCol = 0;
    int pTam = 4;
    int pHor = 0;

    public boolean CeldaEnElTablero(int a, int b) {
        if (a < 0) {
            return false;
        }
        if (b < 0) {
            return false;
        }
        if (a >= 8) {
            return false;
        }
        if (b >= 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean puedePonerBarco(int tab[][], int tam, int f, int c, int hor) {
        int df = 0, dc = 0;
        if (hor == 1) {
            df = 1;
        } else {
            dc = 1;
        }
        if (df == 1) {
            for (int f2 = f; f2 < f + tam; f2++) {
                if (!CeldaEnElTablero(f2, c)) {
                    return false;
                }
            }
        } else {
            for (int c2 = c; c2 < c + tam; c2++) {
                if (!CeldaEnElTablero(f, c2)) {
                    return false;
                }
            }
        }

        for (int f2 = f - 1; f2 <= f + 1 + df * tam; f2++) {
            for (int c2 = c - 1; c2 <= c + 1 + dc * tam; c2++) {
                if (CeldaEnElTablero(f2, c2)) {
                    if (tab[f2][c2] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void ponerBarco(int tab[][], int tam) {
        int f, c, hor;
        int antiColgar = 999999;
        do {
            f = (int) (Math.random() * 8);
            c = (int) (Math.random() * 8);
            hor = (int) (Math.random() * 2);
            antiColgar--;
        } while (!puedePonerBarco(tab, tam, f, c, hor) && antiColgar > 0);
        if (antiColgar <= 0) {
            JOptionPane.showMessageDialog(null, "El juego ha crasheado");
            System.exit(0);
        }
        int df = 0, dc = 0;
        if (hor == 1) {
            df = 1;
        } else {
            dc = 1;
        }
        if (df == 1) {
            for (int f2 = f; f2 < f + tam; f2++) {

                tab[f2][c] = tam;

            }
        } else {
            for (int c2 = c; c2 < c + tam; c2++) {
                tab[f][c2] = tam;

            }

        }
    }

    public void IniciarPartida() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tablero1[i][j] = 0;
                Tablero2[i][j] = 0;
            }
        }
        for (int tam = 1; tam <= 5; tam++) {
            ponerBarco(Tablero2, tam);
        }
    }

    /**
     * Creates new form Ventana1
     */
    public Ventana1() {
        Imagenes i = new Imagenes();
        portada = i.cargar("portada1.jpg");
        tablero = i.cargar("tablero1.jpg");
        initComponents();
        setBounds(0, 0, 800, 600);
        addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nEstado == 0) {
                    nEstado = 1;
                    IniciarPartida();
                    repaint();
                }else if(nEstado==1){
                    int pDF = 0;
                    int pDC = 0;
                    if (pHor==1){
                        pDF=1;
                    }else{
                        pDC=1;
                    }
                    for (int m =pFila; m < pFila + pTam * pDF ; m++) {
                            for (int n =pCol; n < pCol + pTam * pDC ; n++) {
                            
                            Tablero1[n][m]=pTam;
      
                                }

                            }
                }
            }
        }
        );
        addMouseMotionListener(
                new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if (x >= 85 && y >= 230 && x < 85 + 30 * 8 && y < 230 + 30 * 8) {
                    int f = (y - 230) / 30;
                    int c = (x - 100) / 30;

                    if (f != pFila || c != pCol) {
                        pFila=f;
                        pCol=c;
                        if (nEstado == 1) {
                        int pDF = 0;
                        int pDC = 0;
                        if (pHor == 1) {
                            pDF = 1;
                        } else {
                            pDC = 1;
                        }
                        if(pFila + pTam * pDF>=8){
                        pFila=8-pTam * pDF;
                        }
                        if(pCol + pTam * pDC>=8){
                        pCol=8-pTam * pDC;
                        }
                        repaint();
                       //System.out.println("x: "+x+"\ny: "+y);
                    }
                    if (nEstado == 1) {
                        int pDF = 0;
                        int pDC = 0;
                        if (pHor == 1) {
                            pDF = 1;
                        } else {
                            pDC = 1;
                        }
                        for (int m =pFila; m < pFila + pTam * pDF ; m++) {
                            for (int n =pCol; n < pCol + pTam * pDC ; n++) {
                            
                            Tablero1[n][m]=pTam;
      
                                }

                            }

                        }
                    }

                }
            }
        }
        );

    }
    public void PintarTablero(Graphics g, int arr[][], int x, int y) {
        for (int n = 0; n < 8; n++) {
            for (int m = 0; m < 8; m++) {
                if (arr[n][m] > 0) {
                    g.setColor(Color.red);
                    g.fillRect(x + n * 30, y + m * 30, 30, 30);

                } else {
                    g.setColor(Color.black);
                    g.drawRect(x + n * 30, y + m * 30, 30, 30);
                    
                    
                    if (nEstado == 1 && arr==Tablero1) {
                        int pDF = 0;
                        int pDC = 0;
                        
                        if (pHor == 1) {
                            pDF = 1;
                        } else {
                            pDC = 1;
                        }
                        int Estado2 = 1;
                        if (Estado2 == 1){
                        if (m<=pFila && n<=pCol && m>=pFila+pTam*pDF && n>=pCol+pTam*pDC){
                        g.setColor(Color.green);
                        g.fillRect(y+m*30, x+n*30, 30, 30);
                        
                        }else{
                        if (m>=pFila && n>=pCol && m<=pFila+pTam*pDF && n<=pCol+pTam*pDC){
                        g.setColor(Color.green);
                        g.fillRect(x+n*30, y+m*30, 30, 30);
                        }
                        }
                    }}

                }

            }

        }
    }

    @Override
    public void paint(Graphics g) {

        if (nEstado == 0) {
            g.drawImage(portada, 0, 0, this);
        } else {
            g.drawImage(tablero, 0, 0, this);
            PintarTablero(g, Tablero1, 85, 230);
            PintarTablero(g, Tablero2, 440, 230);

        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setText("Iniciar Partida");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana1().setVisible(true);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
