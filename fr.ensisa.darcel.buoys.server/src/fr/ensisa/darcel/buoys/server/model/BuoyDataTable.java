package fr.ensisa.darcel.buoys.server.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.ensisa.darcel.buoys.server.model.Battery.Plug;

public class BuoyDataTable {

	private Map<Long, Map<Long, BuoyData>> table;

	static int level = 100;
	private BuoyData tick (long id) {
		long date = System.currentTimeMillis();
		Location location = new Location (2.34F, 5.67F, 800.0F);
		State state = new State (0, 0);
		Battery battery = new Battery (level, 20, 1, Plug.DISCONNECTED, 100, 1);
		level -= 10;
		BuoyData tick = new BuoyData(date, id, location, state, battery);
		return tick;
	}

	private BuoyData measures (long id) {
		long date = System.currentTimeMillis();
		Location location = new Location (2.34F, 5.67F, 800.0F);
		Measures measures = new Measures();
		measures.setAcceleration_X(1.0F);
		measures.setAcceleration_Y(2.0F);
		measures.setAcceleration_Z(3.0F);
		measures.setRotation_X(10.0F);
		measures.setRotation_Y(20.0F);
		measures.setRotation_Z(30.0F);
		measures.setNorth(65.0F);
		measures.setTop_temperature(20.0F);
		measures.setTop_humidity(60.0F);
		measures.setTop_light(90.0F);
		measures.setTop_ir(25.0F);
		measures.setBottom_temperature(4.0F);
		measures.setBottom_humidity(100.0F);
		measures.setBottom_light(1.0F);
		measures.setBottom_ir(0.0F);
		measures.setTelemetry_front(100.0F);
		measures.setTelemetry_back(200.0F);
		measures.setTelemetry_left(110.0F);
		measures.setTelemetry_right(90.0F);
		BuoyData m = new BuoyData(date, id, location, measures);
		return m;
	}

	private void feed() {
		add (tick(1)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(2)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(2)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(3)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(3)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(4)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(4)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(4)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(4)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(4)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (tick(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
		add (measures(5)); try { Thread.sleep (10); } catch (Exception e) {}
	}

	public Map<Long, Map<Long, BuoyData>> getTable() {
		if (table == null) {
			table = new TreeMap<>();
			feed();
		}
		return table;
	}

	public Map<Long, BuoyData> getById (long id) {
		if (! getTable().containsKey(id)) return null;
		return getTable().get(id);
	}

	private Map<Long, BuoyData> getAndCreateById (long id) {
		if (! getTable().containsKey(id)) {
			Map<Long, BuoyData> previousBuoyData = new TreeMap<>(Collections.reverseOrder());
			getTable().put(id, previousBuoyData);
			return previousBuoyData;
		}
		return getTable().get(id);
	}

	private boolean checkUnicity (List<BuoyData> incoming) {
		long id = incoming.get(0).getId();
		for (BuoyData d : incoming) {
			if (d.getId() != id) return false;
		}
		return true;
	}

	private void homogeusAdd(List<BuoyData> incoming) {
		long id = incoming.get(0).getId();
		Map<Long, BuoyData> previousBuoyData = getAndCreateById (id);
		for (BuoyData d : incoming) {
			previousBuoyData.put(d.getDate().getTime(), d);
		}
	}

	private void heterogeusAdd(List<BuoyData> incoming) {
		for (BuoyData d : incoming) {
			add (d);
		}
	}

	public void add(BuoyData incoming) {
		if (incoming == null) return;
		long id = incoming.getId();
		Map<Long, BuoyData> previousBuoyData = getAndCreateById (id);
		previousBuoyData.put(incoming.getDate().getTime(), incoming);
	}

	public void add(List<BuoyData> incoming) {
		if (incoming == null) return;
		if (incoming.isEmpty()) return;
		if (checkUnicity(incoming)) homogeusAdd(incoming);
		else heterogeusAdd(incoming);
	}

	public BuoyData getLastTick (long id) {
		Map<Long, BuoyData> data = getById (id);
		if (data == null) return null;
		for (BuoyData d : data.values()) {
			if (d.isTick()) return d;
		}
		return null;
	}

	public Map<Long, BuoyData> getByCriterion (long id, boolean tick, boolean measures) {
		if (! getTable().containsKey(id)) return null;
		return getTable().get(id);
	}

	public boolean clear (long id) {
		if (! getTable().containsKey(id)) return false;
		getTable().get(id).clear();
		return true;
	}

}
