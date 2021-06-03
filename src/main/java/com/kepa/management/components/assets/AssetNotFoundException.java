package com.kepa.management.components.assets;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak zasobu o takim ID")
public class AssetNotFoundException extends RuntimeException {
}
