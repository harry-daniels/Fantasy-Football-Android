package com.daniels.harry.assignment.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.daniels.harry.assignment.model.FantasyPlayer;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class TeamEditorViewModel {

    private String budget;

    private FantasyPlayerViewModel gk;
    private FantasyPlayerViewModel lb;
    private FantasyPlayerViewModel lcb;
    private FantasyPlayerViewModel rcb;
    private FantasyPlayerViewModel rb;
    private FantasyPlayerViewModel lm;
    private FantasyPlayerViewModel lcm;
    private FantasyPlayerViewModel rcm;
    private FantasyPlayerViewModel rm;
    private FantasyPlayerViewModel ls;
    private FantasyPlayerViewModel rs;

    public TeamEditorViewModel() {

    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public FantasyPlayerViewModel getGk() {
        return gk;
    }

    public void setGk(FantasyPlayerViewModel gk) {
        this.gk = gk;
    }

    public FantasyPlayerViewModel getLb() {
        return lb;
    }

    public void setLb(FantasyPlayerViewModel lb) {
        this.lb = lb;
    }

    public FantasyPlayerViewModel getLcb() {
        return lcb;
    }

    public void setLcb(FantasyPlayerViewModel lcb) {
        this.lcb = lcb;
    }

    public FantasyPlayerViewModel getRcb() {
        return rcb;
    }

    public void setRcb(FantasyPlayerViewModel rcb) {
        this.rcb = rcb;
    }

    public FantasyPlayerViewModel getRb() {
        return rb;
    }

    public void setRb(FantasyPlayerViewModel rb) {
        this.rb = rb;
    }

    public FantasyPlayerViewModel getLm() {
        return lm;
    }

    public void setLm(FantasyPlayerViewModel lm) {
        this.lm = lm;
    }

    public FantasyPlayerViewModel getLcm() {
        return lcm;
    }

    public void setLcm(FantasyPlayerViewModel lcm) {
        this.lcm = lcm;
    }

    public FantasyPlayerViewModel getRcm() {
        return rcm;
    }

    public void setRcm(FantasyPlayerViewModel rcm) {
        this.rcm = rcm;
    }

    public FantasyPlayerViewModel getRm() {
        return rm;
    }

    public void setRm(FantasyPlayerViewModel rm) {
        this.rm = rm;
    }

    public FantasyPlayerViewModel getLs() {
        return ls;
    }

    public void setLs(FantasyPlayerViewModel ls) {
        this.ls = ls;
    }

    public FantasyPlayerViewModel getRs() {
        return rs;
    }

    public void setRs(FantasyPlayerViewModel rs) {
        this.rs = rs;
    }
}
