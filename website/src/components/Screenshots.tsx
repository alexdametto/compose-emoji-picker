import Image from "next/image";
import styles from "./Screenshots.module.scss";

export function Screenshots() {
  return (
    <section className={`section ${styles.section}`}>
      <div className="container">
        <div className={styles.head}>
          <span className="eyebrow">In your app</span>
          <h2 className={styles.heading}>Light and dark, both native</h2>
          <p className={styles.body}>
            The picker fully supports Light and Dark mode, automatically
            adapting to the colors defined in your Material 3 theme — no
            extra wiring required.
          </p>
        </div>
        <div className={styles.row}>
          <div className={styles.device}>
            <div className={styles.frame}>
              <Image
                src="/images/day_mode.png"
                alt="Compose Emoji Picker in light mode"
                width={1080}
                height={2424}
                sizes="220px"
                priority
              />
            </div>
            <span className={styles.label}>Light</span>
          </div>
          <div className={styles.device}>
            <div className={styles.frame}>
              <Image
                src="/images/night_mode.png"
                alt="Compose Emoji Picker in dark mode"
                width={1080}
                height={2424}
                sizes="220px"
                priority
              />
            </div>
            <span className={styles.label}>Dark</span>
          </div>
        </div>
      </div>
    </section>
  );
}
