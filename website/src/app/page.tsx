import { Hero } from "@/components/Hero";
import { Features } from "@/components/Features";
import { SearchHighlight } from "@/components/SearchHighlight";
import { Screenshots } from "@/components/Screenshots";
import { Install } from "@/components/Install";
import { ApiReference } from "@/components/ApiReference";
import { Footer } from "@/components/Footer";

export default function Home() {
  return (
    <main>
      <Hero />
      <Features />
      <SearchHighlight />
      <Screenshots />
      <Install />
      <ApiReference />
      <Footer />
    </main>
  );
}
