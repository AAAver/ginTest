package miscelaneous;

public class Catalog {
//======================== ПРОВЕРКИ ============================//
	public static class inspection {
		public class theme {
			public static final String UBS_819_IDENT = "самострой";
			public static final String TERRITORY_MONITORING = "Мониторинг закрепленных";
			public static final String ONF = "Мероприятия в отношении ОНФ";
		}

		public class result {
			public static final String PRIL_2 = "Незаконное размещение объекта";
			public static final String PRIL_3 = "Нецелевое использование ЗУ";
			public static final String VIOLS_IDENT = "Выявлены нарушения";
			public static final String FED_PROPERTY = "Федеральная собственность";
			public static final String PRIV_PROPERTY = "Частная собственность";
			public static final String VIOL_SIGNS_IDENT = "Выявлены признаки";
		}

		public enum Reason {
			Proposal("Поручение"),
			Anyс("любая хрень");

			private String reason;

			Reason(String reason) {
				this.reason = reason;
			}

			public String get(){
				return reason;
			}

		}
	}

//======================== ДОКУМЕНТЫ ============================//
	public static class docs {
		public static class category {
			public static final String ACT_PRIL_2 = "АКТ 819-ПП о незаконном размещении";
			public static final String ACT_PRIL_3 = "АКТ 819-ПП о нецелевом использовании";
			public static final String PROTOCOL = "Протокол";
			public static final String RAPORT = "Рапорт";
			public static final String WARNING = "Предостережение";
			public static final String ACT_NF = "Акт осмотра нежилого помещения (здания)";
			public static final String[] DGI_PACK = {"ДГИ - Акт о подтверждении самостроя", "ДГИ - Данные Главархива",
					"ДГИ - Данные ЗПО", "ДГИ - Данные Мосгосстройнадзора", "ДГИ - Данные Мосгосэкспертизы",
					"ДГИ - Данные Москомархитектуры", "ДГИ - Данные о наличии прав собственности",
					"ДГИ - Данные о собственниках", "ДГИ - Материалы инвентарного дела", "ДГИ - Фототаблица"};
			public static final String[] GBU_DISMANTLE_DOC_PACK = {"Акт о приемке выполненных работ КС-2","Уведомление в Мосгосстройнадзор о завершении сноса"};
		}

		public static class path {
			public static final String ACT_PRIL_2 = "docfiles/АКТ о подтверждении факта наличия незаконно размещенного объекта_.docx";
			public static final String ACT_PRIL_3 = "docfiles/АКТ об отсутствии-подтверждении факта незаконного использования ЗУ_.docx";
			public static final String PROTOCOL = "docfiles/Протокол.docx";
			public static final String ACT_NF = "docfiles/Акт осмотра нежилого помещения (здания) принадлежащего на праве собственности г Москве_.pdf";
			public static final String RAPORT = "docfiles/Рапорт об обнаружении административного нарушения_.docx";
			public static final String WARNING = "docfiles/Предостережение.docx";
			public static final String[] DGI_PACK = {"docfiles/ДГИ - Акт о подтверждении самостроя.docx", "docfiles/ДГИ - Данные Главархива.docx", 
					"docfiles/ДГИ - Данные ЗПО.docx", "docfiles/ДГИ - Данные Мосгосстройнадзора.docx", "docfiles/ДГИ - Данные Мосгосэкспертизы.docx",
					"docfiles/ДГИ - Данные Москомархитектуры.docx", "docfiles/ДГИ - Данные о наличии прав собственности.docx","docfiles/ДГИ - Данные о собственниках.docx",
					"docfiles/ДГИ - Материалы инвентарного дела.docx", "docfiles/ДГИ - Фототаблица.docx"};
			public static final String[] GBU_DISMANTLE_DOC_PACK = {"docfiles/Предостережение.docx","docfiles/Предостережение.docx"};
		}

	}

//======================== НАРУШЕНИЯ ============================//	
	public class violations {
		public static final String LAND_MISUSE = "Использование земельного участка не по целевому назначению";
	}

//======================== ОСС ============================//
	public class ubs {

		public class resolution {
			public static final String PP_234 = "234";
			public static final String PP_819 = "819";
		}

		public class state {
			public static final String HAS_UBS_SIGNS = "Обладает признаками СС";
			public static final String TAKEN_INTO_ACCOUNT = "Учтен";
			public static final String INCLUDED = "Включен";
			public static final String INCLUDED_IN_829_PP = "Включен в 829-ПП";
			public static final String SAVED = "Сохранен";
			public static final String UBS_SIGNS_CONFIRMED = "Признаки СС подтверждены";
			public static final String PP_614 = "614-ПП";
			public static final String NOT_A_819_PP = "Не предмет 819-ПП";
			public static final String GZK_DECISION = "Принято решение ГЗК";
			public static final String NOT_A_614_PP = "Не предмет 614-ПП";
			public static final String UBS_SIGNS_NOT_CONFIRMED = "Признаки СС не подтверждены";
			public static final String AGREED_TO_INCLUDE = "Согласован для включения";
			public static final String AGREED_TO_EXCLUDE = "Согласован для исключения";
		}
	}

//======================== СХД ============================//
	public class shd {
		public static final String DEFAULT_SHD = "ЮГОРИЯ";
	}

	public class area {
		public class ao {
			public static final String CAO = "ЦАО";
		}
	}

//======================== ПРАВО ИСПОЛЬЗОВАНИЯ ============================//
	public class useRight {
		public static final String RENT = "Договор аренды";
		public static final String RIGHT_OF_USE = "пользования";
	}

	// ============== ВЗЫСКАНИЯ ===================== //
	public class legalCase{
		public class status{
			public static final String PREPARE_CASE = "подготовка иска в суд";
		}
	}

	public enum Array{;

		public enum NestedArray1{
			Variant1("Bom"),
			Variant2("Bam");
			String variant;
			NestedArray1(String variant) {
				this.variant = variant;
			}
			public String toString(){
				return variant;
			}
		}
		public enum NestedArray2{
			Variant3("Bim"),
			Variant4("Bum");
			String variant;
			NestedArray2(String variant) {
				this.variant = variant;
			}
			public String toString(){
				return variant;
			}
		}

	}
}
