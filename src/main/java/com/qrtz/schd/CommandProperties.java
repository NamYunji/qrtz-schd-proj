package com.qrtz.schd;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * lombok dependency
 * @Data
 *  - Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode
 * @NoArgsConstructor
 *  - RequiredArgsConstructor, AllArgsConstructor
 * @AllArgsConstructor
 *  - NoArgsConstructor, RequiredArgsConstructor
 */
@Component
@ConfigurationProperties("ip.command")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandProperties {
	
	private String scheduleFile;
}
