"use client";

import { useState } from "react";
import styles from "./Install.module.scss";

const TABS = [
  {
    id: "settings",
    label: "settings.gradle.kts",
    step: "1. Add the repository",
    code: `dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}`,
  },
  {
    id: "build",
    label: "build.gradle.kts",
    step: "2. Add the dependency",
    code: `dependencies {
    implementation("com.github.alexdametto:compose-emoji-picker:<version>")
}`,
  },
  {
    id: "usage",
    label: "Usage.kt",
    step: "3. Use it in a composable",
    code: `val openEmojiPicker: MutableState<Boolean> = remember {
    mutableStateOf(false)
}
val selectedEmoji: MutableState<String> = remember {
    mutableStateOf("😀")
}

EmojiPicker(
    open = openEmojiPicker.value,
    onClose = {
        openEmojiPicker.value = false
    },
    onEmojiSelected = {
        selectedEmoji.value = it.emoji
        openEmojiPicker.value = false
    }
)`,
  },
];

export function Install() {
  const [active, setActive] = useState(0);
  const tab = TABS[active];

  return (
    <section className={`section ${styles.section}`} id="install">
      <div className="container">
        <div className={styles.head}>
          <span className="eyebrow">Install</span>
          <h2 className={styles.heading}>Three steps, one dependency</h2>
        </div>

        <div className={styles.editor}>
          <div className={styles.tabs} role="tablist">
            {TABS.map((t, i) => (
              <button
                key={t.id}
                role="tab"
                aria-selected={i === active}
                className={`${styles.tab} ${i === active ? styles.tabActive : ""}`}
                onClick={() => setActive(i)}
              >
                {t.label}
              </button>
            ))}
          </div>
          <span className={styles.stepLabel}>{tab.step}</span>
          <pre className={styles.pre}>
            <code>{tab.code}</code>
          </pre>
        </div>
      </div>
    </section>
  );
}
