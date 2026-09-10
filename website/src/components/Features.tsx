import styles from "./Features.module.scss";

const FEATURES = [
  {
    icon: "😀",
    title: "Full emoji set",
    text: "A wide range of emojis compatible with the latest Unicode standards.",
  },
  {
    icon: "🔎",
    title: "Quick search",
    text: "Find emojis fast by category or name, right from the picker.",
  },
  {
    icon: "⌚",
    title: "Recent emojis",
    text: "The last emojis a user picked stay one tap away, every time.",
  },
  {
    icon: "🌎",
    title: "Multilingual support",
    text: "English and Italian, in both the UI and the search — more languages welcome via PR.",
  },
  {
    icon: "📱",
    title: "Full Compose compatibility",
    text: "Built to drop into any Jetpack Compose screen without friction.",
  },
];

export function Features() {
  return (
    <section className={`section ${styles.section}`} id="features">
      <div className="container">
        <div className={styles.head}>
          <span className="eyebrow">What you get</span>
          <h2 className={styles.heading}>Everything a picker needs, nothing it doesn&apos;t</h2>
        </div>
        <div className={styles.grid}>
          {FEATURES.map((f) => (
            <div className={styles.card} key={f.title}>
              <span className={styles.icon}>{f.icon}</span>
              <span className={styles.cardTitle}>{f.title}</span>
              <p className={styles.cardText}>{f.text}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
