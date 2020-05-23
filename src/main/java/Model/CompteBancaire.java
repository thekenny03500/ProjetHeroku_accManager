/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Aimeric-Thomas
 */
public class CompteBancaire 
{
    private int _id;
    private String _nom;
    private String _prenom;
    private Float _account;
    private ERisk _risk;
    
    public CompteBancaire(){}

    public CompteBancaire(int _id, String _nom, String _prenom, Float _account, ERisk _risk) {
        this._id = _id;
        this._nom = _nom;
        this._prenom = _prenom;
        this._account = _account;
        this._risk = _risk;
    }

    public int getId() {
        return _id;
    }
    
    public String getNom() {
        return _nom;
    }

    public void setNom(String _nom) {
        this._nom = _nom;
    }

    public String getPrenom() {
        return _prenom;
    }

    public void setPrenom(String _prenom) {
        this._prenom = _prenom;
    }

    public Float getAccount() {
        return _account;
    }

    public void setAccount(Float _account) {
        this._account = _account;
    }

    public ERisk getRisk() {
        return _risk;
    }

    public void setRisk(ERisk _risk) {
        this._risk = _risk;
    }
}
