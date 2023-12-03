package com.example.backend.entity.profile;

public enum ESkill {
    // Frontend
    JAVASCRIPT("JavaScript"),
    HTML("HTML"),
    CSS("CSS"),
    TYPESCRIPT("TypeScript"),
    REACT("React"),
    ANGULAR("Angular"),
    VUE("Vue"),
    SASS("Sass"),
    LESS("LESS"),

    // Backend
    JAVA("Java"),
    SPRING("Spring"),
    PYTHON("Python"),
    DJANGO("Django"),
    NODE_JS("Node.js"),
    EXPRESS_JS("Express.js"),
    RUBY("Ruby"),
    PHP("PHP"),
    LARAVEL("Laravel"),

    // Mobile
    SWIFT("Swift"),
    KOTLIN("Kotlin"),
    ANDROID("Android"),
    REACT_NATIVE("React Native"),
    FLUTTER("Flutter"),
    XAMARIN("Xamarin"),

    // Database
    SQL("SQL"),
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    MONGODB("MongoDB"),
    FIREBASE("Firebase"),
    SQLITE("SQLite"),
    ORACLE("Oracle"),
    REDIS("Redis"),

    // DevOps
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    JENKINS("Jenkins"),
    GIT("Git"),
    GITHUB("GitHub"),
    GITLAB("GitLab"),
    BITBUCKET("Bitbucket"),
    ANSIBLE("Ansible"),
    PUPPET("Puppet"),

    // Machine Learning
    TENSORFLOW("TensorFlow"),
    PYTORCH("PyTorch"),
    SCIKIT_LEARN("Scikit learn"),
    KERAS("Keras"),
    OPENCV("OpenCV"),

    // Game Development
    UNITY("Unity"),
    C_SHARP("C#"),
    UNREAL_ENGINE("Unreal Engine"),
    CPP("C++"),
    GODOT("Godot"),

    // Cybersecurity
    WIRESHARK("Wireshark"),
    METASPLOIT("Metasploit"),
    BURP_SUITE("Burp Suite"),
    SNORT("Snort"),
    OWASP_ZAP("OWASP ZAP");

    private final String name;

    ESkill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ESkill from(String name) {
        for (ESkill skill : ESkill.values()) {
            if (skill.getName().equalsIgnoreCase(name)) {
                return skill;
            }
        }
        throw new IllegalArgumentException("there is no skill about " + name);
    }
}

