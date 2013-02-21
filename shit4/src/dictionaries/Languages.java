package dictionaries;

public enum Languages {
	English{
		@Override
		public String toString() {
			return "English";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Translate2English();
		}
	},
	
	Hebrew{
		@Override
		public String toString() {
			return "Hebrew";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Translate2Hebrew();
		}
	}
	;
	
	public abstract IDictionary getDictionary();
}
