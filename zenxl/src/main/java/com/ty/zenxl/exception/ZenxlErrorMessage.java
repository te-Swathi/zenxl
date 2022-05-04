package com.ty.zenxl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZenxlErrorMessage {

	private boolean isError;
	private String message;

}
