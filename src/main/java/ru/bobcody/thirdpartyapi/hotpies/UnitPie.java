package ru.bobcody.thirdpartyapi.hotpies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UnitPie {
    private String shareURL;
    private String shareText;

    public UnitPie(String shareURL, String shareText) {
        this.shareURL = shareURL;
        this.shareText = shareText;
    }

    @Override
    public String toString() {
        return shareText + "\n" + shareURL;
    }
}