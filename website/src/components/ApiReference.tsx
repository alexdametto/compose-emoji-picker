import styles from "./ApiReference.module.scss";

const PROPS = [
  {
    prop: "open",
    type: "Boolean",
    default: "—",
    required: true,
    desc: "Whether the picker is visible.",
  },
  {
    prop: "onClose",
    type: "() -> Unit",
    default: "—",
    required: true,
    desc: "Called when the picker should close.",
  },
  {
    prop: "onEmojiSelected",
    type: "(emoji: Emoji) -> Unit",
    default: "—",
    required: true,
    desc: "Called when the user taps an emoji.",
  },
  {
    prop: "colors",
    type: "EmojiPickerColors",
    default: "EmojiPickerDefaults.emojiPickerColors()",
    required: false,
    desc: "Colors used to paint the picker.",
  },
];

const COLOR_FIELDS = [
  {
    field: "backgroundColor",
    token: "surfaceContainerLow",
    desc: "Background of the picker's container.",
  },
  {
    field: "searchBarBackgroundColor",
    token: "surfaceVariant",
    desc: "Fill color of the search field.",
  },
  {
    field: "searchBarIconTint",
    token: "onSurfaceVariant",
    desc: "Color of the search icon.",
  },
  {
    field: "searchBarTextColor",
    token: "onSurfaceVariant",
    desc: "Text and placeholder color inside the search field.",
  },
  {
    field: "textColor",
    token: "onSurfaceVariant",
    desc: "Color for category titles and the empty-state message.",
  },
  {
    field: "activeCategoryTint",
    token: "primary",
    desc: "Icon tint for the selected category tab.",
  },
  {
    field: "inactiveCategoryTint",
    token: "onSurface",
    desc: "Icon tint for unselected category tabs.",
  },
];

const EMOJI_FIELDS = [
  { field: "id", type: "String", desc: "Unique key for the emoji." },
  { field: "emoji", type: "String", desc: "The emoji glyph itself." },
  {
    field: "name",
    type: "String",
    desc: "Human-readable name, resolved to the matched search language.",
  },
  { field: "slug", type: "String", desc: "Identifier-friendly form of the name." },
  { field: "category", type: "String", desc: "The Unicode category this emoji belongs to." },
];

export function ApiReference() {
  return (
    <section className={`section ${styles.section}`} id="api">
      <div className="container">
        <div className={styles.head}>
          <span className="eyebrow">API reference</span>
          <h2 className={styles.heading}>Every parameter, every default</h2>
          <p className={styles.body}>
            The full signature of the <code>EmojiPicker</code> composable —
            three required callbacks and one optional color set that follows
            your Material 3 theme unless you override it.
          </p>
        </div>

        <span className={styles.blockLabel}>
          <code>EmojiPicker(...)</code>
        </span>
        <div className={styles.tableWrap}>
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Prop</th>
                <th>Type</th>
                <th>Default</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {PROPS.map((p) => (
                <tr key={p.prop}>
                  <td className={styles.prop}>{p.prop}</td>
                  <td className={styles.type}>{p.type}</td>
                  <td className={styles.default}>
                    {p.required ? (
                      <span className={styles.required}>required</span>
                    ) : (
                      p.default
                    )}
                  </td>
                  <td className={styles.desc}>{p.desc}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <span className={styles.blockLabel}>
          <code>EmojiPickerColors</code> fields — defaults read from your{" "}
          <code>MaterialTheme.colorScheme</code>
        </span>
        <div className={styles.tableWrap}>
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Field</th>
                <th>Default token</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {COLOR_FIELDS.map((c) => (
                <tr key={c.field}>
                  <td className={styles.prop}>{c.field}</td>
                  <td className={styles.type}>
                    MaterialTheme.colorScheme.{c.token}
                  </td>
                  <td className={styles.desc}>{c.desc}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <span className={styles.blockLabel}>
          <code>Emoji</code> — passed to <code>onEmojiSelected</code>
        </span>
        <div className={styles.tableWrap}>
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Field</th>
                <th>Type</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {EMOJI_FIELDS.map((f) => (
                <tr key={f.field}>
                  <td className={styles.prop}>{f.field}</td>
                  <td className={styles.type}>{f.type}</td>
                  <td className={styles.desc}>{f.desc}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
