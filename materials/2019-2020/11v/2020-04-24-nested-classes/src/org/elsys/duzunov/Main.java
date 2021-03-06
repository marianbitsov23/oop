package org.elsys.duzunov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

// Външен клас
class OuterClass {
    static int x = 0;

    int foo;

    public OuterClass(int foo) {
        this.foo = foo;
    }

    /* Статичен вложен клас:
     * - можем да си ги мислим като статични членове на външния клас
     * (в main метода по долу може да видим, че се достъпват по подобен начин -
     * статичните вложени класове са свързани към външните класове, в които са
     * вложени)
     * - използват се, когато два класа е логично да бъдат групирани заедно
     * (например когато вложения клас е помощен за имплементирането на някаква
     * функционалност на външния клас; примери: компаратори и класове за
     * изключения)
     */
    static class NestedStaticClass {
        void f() {
            // Могат да достъпват само статичните полета на външния клас
            System.out.println(x);

            // Не могат да достъпват нестатичните полета на външния клас
            // System.out.println(foo); // компилационна грешка!

            // Не могат да достъпват нестатичните методи на външния клас
            // g(); // компилационна грешка!

            // Но разбира се можем да достъпваме полетата и методите
            // (дори да са private) на външния клас през негови инстанции
            new OuterClass(42).g();
        }
    }

    /* Нестатичен вложен клас (още наречен - вътрешен клас):
     * - можем да си ги мислим като нестатични членове на външния клас
     * (в main метода по долу може да видим, че се достъпват през обекти на
     * външния клас - нестатичните вложени класове се свързват с конкретни
     * обекти на външните класове, в които са вложени)
     * - не могат да имат статични полета и методи, тъй като са свързани с
     * конкретни инстанции (обекти) на външния клас
     * - използват се, когато дадена функционалност е смислено да бъде обособена
     * в отделен клас (за да се използва многократно за един и същи обект,
     * например), но обектите от този клас са пряко свързани с обектите на
     * външния клас и се нуждаят от достъп до нестатичните методи и полета на
     * външния клас (по този начин запазваме енкапсулацията на външния клас -
     * полетата му могат да останат private, тъй като вътрешният клас може да ги
     * достъпва); друго предимство - четимост на кода ни
     * - примери за използване: Iterator класове за класове, представящи
     * структури от данни, които могат да бъдат обхождани (вижте класа Histogram
     * за пример от реалния свят)
     */
    class InnerClass {
        int foo = 5;

        void bar() {
            // Може да достъпваме както статичните полета и методи
            // (дори да са private), така и нестатичните

            int foo = 4;
            // Достъп до локалната променлива foo
            System.out.println(foo); // 4
            // Достъп до полето foo на текущия InnerClass обект
            System.out.println(this.foo); // 5
            // Достъп до полето foo на текущия OuterClass обект
            System.out.println(OuterClass.this.foo);
            // Достъп до полето foo на новосъздаден OuterClass обект
            System.out.println(new OuterClass(42).foo);

            g();
        }
    }

    public void g() {
    }
}

public class Main {
    public static void main(String[] args) {
        // Начин да достъпим вложен статичен клас
        OuterClass.NestedStaticClass baz = new OuterClass.NestedStaticClass();
        // По подобен начин достъпваме статичните полета и методи
        System.out.println(OuterClass.x);

        OuterClass outerObject = new OuterClass(42);
        // Достъпваме нестатичен вложен клас само през инстанции (обекти) на
        // външния клас
        OuterClass.InnerClass x1 = outerObject.new InnerClass();
        // Както достъпваме и нестатичните полета и методи
        System.out.println(outerObject.foo);

        OuterClass.InnerClass x2 = new OuterClass(20).new InnerClass();
        x1.bar();
        x2.bar();

        // Не можем да достъпваме нестатичен вложен клас директно като
        // статичен член на външния клас - компилационна грешка!
        // OuterClass.InnerClass y = new OuterClass.InnerClass();

        ArrayList<Integer> integers =
                new ArrayList<>(Arrays.asList(1, 2, 1, 42, -1, 42, 1, 10, 33));
        Histogram<Integer> histogram = new Histogram<>(integers);
        System.out.println("Histogram:");
        for (Map.Entry<Integer, Integer> entry : histogram) {
            System.out.println(
                    String.format("(%d, %d)", entry.getKey(), entry.getValue())
            );
        }
    }
}
