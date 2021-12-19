package task1withAI;

public enum Symbol {
    X {
        @Override
        public String toString() {
            return "X";
        }

        @Override
        public Symbol oppose() {
            return O;
        }
    },

    O {
        @Override
        public String toString() {
            return "O";
        }

        @Override
        public Symbol oppose() {
            return X;
        }
    },

    SPACE {
        @Override
        public String toString() {
            return "_";
        }

        @Override
        public Symbol oppose() {
            return null;
        }
    };

    public abstract Symbol oppose();
}