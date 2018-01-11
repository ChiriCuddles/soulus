package yuudaari.soulus.common.config;

import com.google.gson.JsonElement;

@SuppressWarnings("rawtypes")
public class ManualSerializer extends Serializer {

	public static interface SerializeHandler {

		public JsonElement serialize (Object obj);
	}

	public static interface DeserializeHandler {

		public Object deserialize (JsonElement json, Object current);
	}

	public SerializeHandler serializeHandler;
	public DeserializeHandler deserializeHandler;

	public ManualSerializer (SerializeHandler serializeHandler, DeserializeHandler deserializeHandler) {
		this.serializeHandler = serializeHandler;
		this.deserializeHandler = deserializeHandler;
	}

	@Override
	public JsonElement serialize (Object from) {
		return serializeHandler.serialize(from);
	}

	@Override
	public Object deserialize (JsonElement from, Object into) {
		return deserializeHandler.deserialize(from, into);
	}
}
