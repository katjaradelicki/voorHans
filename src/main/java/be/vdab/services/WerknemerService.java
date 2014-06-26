package be.vdab.services;

import java.util.Collection;

import be.vdab.entities.Werknemer;

public interface WerknemerService {
Iterable<Werknemer> findAll();
Iterable<Werknemer> findMetHoogsteWedde();
}
