package com.kepa.management.components.assets;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Zasób z takim numerem już istnieje")
class DuplicateException extends RuntimeException {

}
