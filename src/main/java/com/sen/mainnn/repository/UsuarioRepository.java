package com.sen.mainnn.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sen.mainnn.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
