# Лаборатори №9 — Код хамралт: JaCoCo-гоор тестээ хөтлөх

**Оюутан:** Эрдэнэбаяр
**Оюутны код:** B232270136
**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)

---

## 1. Ажлын орчин

```
$ java -version
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment Temurin-17.0.20.1+1 (build 17.0.20.1+1)
OpenJDK 64-Bit Server VM Temurin-17.0.20.1+1 (build 17.0.20.1+1, mixed mode, sharing)

$ mvn -version
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Users\THE_FEDzUP\tools\apache-maven-3.9.9
Java version: 17.0.20.1, vendor: Eclipse Adoptium, runtime: C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

| Зүйл | Утга |
|---|---|
| JDK | Eclipse Temurin OpenJDK 17.0.20.1 |
| Build | Apache Maven 3.9.9 |
| Тест / хамралт | JUnit Jupiter 5.10.2, surefire 3.2.5, JaCoCo 0.8.12 |
| ҮС | Windows 11 Pro (26200), Git Bash (`tee` нь UTF-8 бичдэг тул results/ файлуудыг Git Bash-аар хадгалсан) |

> Starter төслийг (Teams → Class Materials → Labs → Lab09: `pom.xml`, `ScholarshipCalculator.java`)
> өөрчлөлгүй хуулсан. Анхны `mvn verify` → "No tests to run." ба
> "Skipping JaCoCo execution due to missing execution data file." — тайлан үүсээгүй (зааварт заасанчлан).

> **Windows дээрх нэг саад.** Төслийн хавтасны зам кирилл үсэгтэй
> (`…\Программ хангамжийн чанарын баталгаа ба туршилт\lab09`) тул JaCoCo agent-ийн
> `destfile=` зам surefire-ийн эхлүүлэх командад `????` болж, тестийн JVM асахгүй
> **BUILD FAILURE** өгсөн. `pom.xml`-ийг өөрчлөлгүй, ижил хавтас руу заасан ASCII замтай
> junction (`C:\THE_FEDzUP\lab09-jacoco`) үүсгэж, `mvn`-ийг тэндээс ажиллуулсан — тиймээс
> results/ файлуудад `C:\THE_FEDzUP\lab09-jacoco\…` зам харагдана. Linux/WSL болон латин
> замтай хавтсанд энэ асуудал гарахгүй.

## 2. Хэсэг A — Happy path тестүүд ба эхний хэмжилт

5 хэвийн урсгалын тест: `letterGrade_score95_returnsA`, `letterGrade_score85_returnsB`,
`gpa_scores95And85_returns3point5`, `scholarship_gpa39Credits16NoViolation_returns100`,
`scholarship_gpa35Credits12NoViolation_returns70` → `results/mvn-verify-A.txt`:
`Tests run: 5, Failures: 0`, BUILD SUCCESS.

`results/jacoco-A.csv` (ScholarshipCalculator):

| | Missed | Covered | Хамралт |
|---|---|---|---|
| LINE | 10 | 15 | **15/25 = 60.0%** |
| BRANCH | 21 | 20 | **20/41 = 48.8%** |
| INSTRUCTION | 50 | 102 | 102/152 = 67.1% |
| METHOD | 0 | 4 | 4/4 |

Тайлангийн **шар** (хагас хамрагдсан) мөрүүд нь бүгд нөхцөлийн шалгалтууд: `if (score < 0 || score > 100)`
(2/4 салаа дутуу), `if (score >= 80)`, gpa-гийн `scores == null || scores.length == 0`,
`switch` (C, D салаа дутуу), scholarship-ийн эхний `gpa < 0 || gpa > 4 || credits < 0`
(3/6 дутуу), `if (hasViolation)`, хоёр `&&` нөхцөл — өөрөөр хэлбэл зөвхөн "үнэн" эсвэл зөвхөн
"худал" тал нь шалгагдсан. **Улаан** (огт хүрээгүй) мөрүүд нь яг алдааны замууд: гурван
`throw new IllegalArgumentException`, хамгийн доод дүнгүүд (`C`, `D`, `F`) ба тэтгэлгийн 50%,
0% буцаах мөрүүд — happy path тестүүд хүчингүй оролт, муу дүн, бага голчийн замыг огт дайраагүй.
