/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interficie;

/**
 *
 * @author Usuari
 */
public class BillarException extends RuntimeException {
    public BillarException(String message) {
        super(message);
    }

    public BillarException(Throwable cause) {
        super(cause);
    }

    public BillarException(String message , Throwable ex) {
        super(message,ex);
    }
    
}
