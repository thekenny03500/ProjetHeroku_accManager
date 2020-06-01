/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author kenny
 */
public class Approval {
    
    private int _id;
    private int _idCompte;
    private Float _account;
    private EApproval _etat;
    
    public Approval(){}

    public Approval(int _id, int _idCompte, Float _account, EApproval _etat) {
        this._id = _id;
        this._idCompte = _idCompte;
        this._account = _account;
        this._etat = _etat;
    }

    public int getId() {
        return _id;
    }

    public int getIdCompte() {
        return _idCompte;
    }

    public void setIdCompte(int _idCompte) {
        this._idCompte = _idCompte;
    }

    public Float getAccount() {
        return _account;
    }

    public void setAccount(Float _account) {
        this._account = _account;
    }

    public EApproval getEtat() {
        return _etat;
    }

    public void setEtat(EApproval _etat) {
        this._etat = _etat;
    }
}
